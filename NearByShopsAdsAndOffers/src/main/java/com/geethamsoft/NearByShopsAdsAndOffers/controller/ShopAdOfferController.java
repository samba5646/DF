package com.geethamsoft.NearByShopsAdsAndOffers.controller;

import com.geethamsoft.NearByShopsAdsAndOffers.dto.ShopAdOfferDTO;
import com.geethamsoft.NearByShopsAdsAndOffers.dto.ShopAdOfferSearchDTO;
import com.geethamsoft.NearByShopsAdsAndOffers.exception.ResourceNotFoundException;
import com.geethamsoft.NearByShopsAdsAndOffers.model.ShopAdOffer;
import com.geethamsoft.NearByShopsAdsAndOffers.service.ShopAdOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/shopadoffers")
public class ShopAdOfferController {

    @Autowired
    private ShopAdOfferService shopAdOfferService;

    @GetMapping
    public ResponseEntity<List<ShopAdOffer>> getAllShopAdsAndOffers() {
        List<ShopAdOffer> shopAdOffers = shopAdOfferService.getAllShopAdsAndOffers();
        return ResponseEntity.ok(shopAdOffers);
    }

    @PostMapping("/search")
    public ResponseEntity<List<ShopAdOffer>> searchShopAdsAndOffers(@RequestBody ShopAdOfferSearchDTO searchDTO) {
        List<ShopAdOffer> shopAdOffers = shopAdOfferService.searchShopAdsAndOffers(searchDTO);
        return ResponseEntity.ok(shopAdOffers);
    }

    @PostMapping("/add")
    public ResponseEntity<ShopAdOffer> addShopAdOffer(@RequestBody ShopAdOfferDTO shopAdOfferDTO) {
        ShopAdOffer shopAdOffer = shopAdOfferService.addShopAdOffer(shopAdOfferDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(shopAdOffer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShopAdOffer> updateShopAdOffer(
            @PathVariable String id,
            @RequestBody ShopAdOfferDTO shopAdOfferDTO
    ) {
        try {
            ShopAdOffer shopAdOffer = shopAdOfferService.updateShopAdOffer(id, shopAdOfferDTO);
            return ResponseEntity.ok(shopAdOffer);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShopAdOffer(@PathVariable String id) {
        try {
            shopAdOfferService.deleteShopAdOffer(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
