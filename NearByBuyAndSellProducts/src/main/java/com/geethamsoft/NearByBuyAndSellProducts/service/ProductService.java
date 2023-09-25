package com.geethamsoft.NearByBuyAndSellProducts.service;

import com.geethamsoft.NearByBuyAndSellProducts.DTO.ProductDTO;
import com.geethamsoft.NearByBuyAndSellProducts.DTO.ProductSearchDTO;
import com.geethamsoft.NearByBuyAndSellProducts.exception.ResourceNotFoundException;
import com.geethamsoft.NearByBuyAndSellProducts.model.Product;
import com.geethamsoft.NearByBuyAndSellProducts.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> searchProducts(ProductSearchDTO searchDTO) {
        Query query = buildSearchQuery(searchDTO);
        return mongoTemplate.find(query, Product.class);
    }

    private Query buildSearchQuery(ProductSearchDTO searchDTO) {
        Query query = new Query();

        if (searchDTO.getKeyword() != null && !searchDTO.getKeyword().isEmpty()) {
            Criteria keywordCriteria = new Criteria().orOperator(
                    Criteria.where("name").regex(searchDTO.getKeyword(), "i"),
                    Criteria.where("category").regex(searchDTO.getKeyword(), "i"),
                    Criteria.where("location").regex(searchDTO.getKeyword(), "i"),
                    Criteria.where("description").regex(searchDTO.getKeyword(), "i")
            );
            query.addCriteria(keywordCriteria);
        }

        if (searchDTO.getCategory() != null && !searchDTO.getCategory().isEmpty()) {
            query.addCriteria(Criteria.where("category").is(searchDTO.getCategory()));
        }

        if (searchDTO.getLocation() != null && !searchDTO.getLocation().isEmpty()) {
            query.addCriteria(Criteria.where("location").is(searchDTO.getLocation()));
        }

        if (searchDTO.getMinPrice() != null) {
            query.addCriteria(Criteria.where("price").gte(searchDTO.getMinPrice()));
        }

        if (searchDTO.getMaxPrice() != null) {
            query.addCriteria(Criteria.where("price").lte(searchDTO.getMaxPrice()));
        }

        if (searchDTO.getCondition() != null && !searchDTO.getCondition().isEmpty()) {
            query.addCriteria(Criteria.where("condition").is(searchDTO.getCondition()));
        }

        if (searchDTO.getAvailability() != null && !searchDTO.getAvailability().isEmpty()) {
            query.addCriteria(Criteria.where("availability").is(searchDTO.getAvailability()));
        }

        if (searchDTO.getSortBy() != null && !searchDTO.getSortBy().isEmpty()) {
            // Implement sorting based on searchDTO.getSortBy()
        }

        return query;
    }

    public Product addProduct(ProductDTO productDTO, MultipartFile file) throws IOException {
        Product product = mapProductDTO(productDTO);
        // Handle image upload and storage logic here
        // Example:
        // byte[] imageBytes = file.getBytes();
        // product.setImage(imageBytes);
        return productRepository.save(product);
    }

    public Product updateProduct(String id, ProductDTO productDTO, MultipartFile file)
            throws ResourceNotFoundException, IOException {
        Product existingProduct = getProductById(id);
        Product updatedProduct = mapProductDTO(productDTO);

        // Handle image update logic here if needed
        // Example:
        // if (file != null && !file.isEmpty()) {
        //     byte[] imageBytes = file.getBytes();
        //     existingProduct.setImage(imageBytes);
        // }

        // Copy updated fields to the existing product
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setCategory(updatedProduct.getCategory());
        existingProduct.setLocation(updatedProduct.getLocation());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setCondition(updatedProduct.getCondition());
        existingProduct.setAvailability(updatedProduct.getAvailability());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setContactInfo(updatedProduct.getContactInfo());

        return productRepository.save(existingProduct);
    }

    public void deleteProduct(String id) throws ResourceNotFoundException {
        Product existingProduct = getProductById(id);
        productRepository.delete(existingProduct);
    }

    public ProductDTO mapProductDTOJson(String productDTOJson) {
        // Implement logic to deserialize the JSON string into a ProductDTO object
        // Example using Jackson ObjectMapper:
        // ObjectMapper objectMapper = new ObjectMapper();
        // try {
        //     return objectMapper.readValue(productDTOJson, ProductDTO.class);
        // } catch (JsonProcessingException e) {
        //     // Handle the exception appropriately
        // }
        return null; // Replace with your implementation
    }

    private Product getProductById(String id) throws ResourceNotFoundException {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            return productOptional.get();
        } else {
            throw new ResourceNotFoundException("Product not found with id: " + id);
        }
    }

    private Product mapProductDTO(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setCategory(productDTO.getCategory());
        product.setLocation(productDTO.getLocation());
        product.setPrice(productDTO.getPrice());
        product.setCondition(productDTO.getCondition());
        product.setAvailability(productDTO.getAvailability());
        product.setDescription(productDTO.getDescription());
        product.setContactInfo(productDTO.getContactInfo());
        return product;
    }
}
