package com.geethamsoft.NearByBuyAndSellProducts.controller;

import com.geethamsoft.NearByBuyAndSellProducts.DTO.ProductDTO;
import com.geethamsoft.NearByBuyAndSellProducts.DTO.ProductSearchDTO;
import com.geethamsoft.NearByBuyAndSellProducts.exception.ResourceNotFoundException;
import com.geethamsoft.NearByBuyAndSellProducts.model.Product;
import com.geethamsoft.NearByBuyAndSellProducts.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestBody ProductSearchDTO searchDTO) {
        List<Product> products = productService.searchProducts(searchDTO);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(
            @RequestParam("file") MultipartFile file,
            @RequestParam("productDTO") String productDTOJson
    ) {
        try {
            ProductDTO productDTO = productService.mapProductDTOJson(productDTOJson);
            Product product = productService.addProduct(productDTO, file);
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Error uploading image."));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(
            @PathVariable String id,
            @RequestParam("file") MultipartFile file,
            @RequestParam("productDTO") String productDTOJson
    ) {
        try {
            ProductDTO productDTO = productService.mapProductDTOJson(productDTOJson);
            Product product = productService.updateProduct(id, productDTO, file);
            return ResponseEntity.ok(product);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Resource not found with id: " + id));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Error uploading image."));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable String id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Resource not found with id: " + id));
        }
    }

    // Define an ErrorResponse class for handling exceptions
    static class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }}

