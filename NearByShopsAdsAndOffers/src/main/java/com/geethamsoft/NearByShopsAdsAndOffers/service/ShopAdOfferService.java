package com.geethamsoft.NearByShopsAdsAndOffers.service;



import com.geethamsoft.NearByShopsAdsAndOffers.dto.ShopAdOfferDTO;
import com.geethamsoft.NearByShopsAdsAndOffers.dto.ShopAdOfferSearchDTO;
import com.geethamsoft.NearByShopsAdsAndOffers.exception.ResourceNotFoundException;
import com.geethamsoft.NearByShopsAdsAndOffers.model.ShopAdOffer;
import com.geethamsoft.NearByShopsAdsAndOffers.repository.ShopAdOfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ShopAdOfferService {
    @Autowired
    private ShopAdOfferRepository shopAdOfferRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<ShopAdOffer> getAllShopAdsAndOffers() {
        return shopAdOfferRepository.findAll();
    }

    public List<ShopAdOffer> searchShopAdsAndOffers(ShopAdOfferSearchDTO searchDTO) {
        Query query = buildSearchQuery(searchDTO);
        return mongoTemplate.find(query, ShopAdOffer.class);
    }

    private Query buildSearchQuery(ShopAdOfferSearchDTO searchDTO) {
        Query query = new Query();

        if (searchDTO.getKeyword() != null && !searchDTO.getKeyword().isEmpty()) {
            Criteria keywordCriteria = new Criteria().orOperator(
                    Criteria.where("shopName").regex(searchDTO.getKeyword(), "i"),
                    Criteria.where("category").regex(searchDTO.getKeyword(), "i"),
                    Criteria.where("location").regex(searchDTO.getKeyword(), "i"),
                    Criteria.where("offerTitle").regex(searchDTO.getKeyword(), "i"),
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

        if (searchDTO.getSortBy() != null && !searchDTO.getSortBy().isEmpty()) {
            // Implement sorting based on searchDTO.getSortBy()
        }

        if (searchDTO.getAvailability() != null && !searchDTO.getAvailability().isEmpty()) {
            query.addCriteria(Criteria.where("availability").is(searchDTO.getAvailability()));
        }

        if (searchDTO.getMinDiscount() != null) {
            query.addCriteria(Criteria.where("discountPercentage").gte(searchDTO.getMinDiscount()));
        }

        if (searchDTO.getMaxDiscount() != null) {
            query.addCriteria(Criteria.where("discountPercentage").lte(searchDTO.getMaxDiscount()));
        }

        if (searchDTO.getMinPurchase() != null) {
            query.addCriteria(Criteria.where("minPurchase").gte(searchDTO.getMinPurchase()));
        }

        if (searchDTO.getShopName() != null && !searchDTO.getShopName().isEmpty()) {
            query.addCriteria(Criteria.where("shopName").is(searchDTO.getShopName()));
        }

        if (searchDTO.getValidUntil() != null) {
            query.addCriteria(Criteria.where("validUntil").gte(searchDTO.getValidUntil()));
        }

        return query;
    }

    public ShopAdOffer addShopAdOffer(ShopAdOfferDTO shopAdOfferDTO) {
        ShopAdOffer shopAdOffer = mapShopAdOfferDTO(shopAdOfferDTO);
        return shopAdOfferRepository.save(shopAdOffer);
    }

    public ShopAdOffer updateShopAdOffer(String id, ShopAdOfferDTO shopAdOfferDTO)
            throws ResourceNotFoundException {
        ShopAdOffer existingShopAdOffer = getShopAdOfferById(id);
        ShopAdOffer updatedShopAdOffer = mapShopAdOfferDTO(shopAdOfferDTO);

        // Copy updated fields to the existing shopAdOffer
        existingShopAdOffer.setShopName(updatedShopAdOffer.getShopName());
        existingShopAdOffer.setCategory(updatedShopAdOffer.getCategory());
        existingShopAdOffer.setLocation(updatedShopAdOffer.getLocation());
        existingShopAdOffer.setOfferTitle(updatedShopAdOffer.getOfferTitle());
        existingShopAdOffer.setDescription(updatedShopAdOffer.getDescription());
        existingShopAdOffer.setValidUntil(updatedShopAdOffer.getValidUntil());
        existingShopAdOffer.setDiscountPercentage(updatedShopAdOffer.getDiscountPercentage());
        existingShopAdOffer.setMinPurchase(updatedShopAdOffer.getMinPurchase());
        existingShopAdOffer.setPromoCode(updatedShopAdOffer.getPromoCode());
        existingShopAdOffer.setContactInformation(updatedShopAdOffer.getContactInformation());

        return shopAdOfferRepository.save(existingShopAdOffer);
    }

    public void deleteShopAdOffer(String id) throws ResourceNotFoundException {
        ShopAdOffer existingShopAdOffer = getShopAdOfferById(id);
        shopAdOfferRepository.delete(existingShopAdOffer);
    }

    private ShopAdOffer getShopAdOfferById(String id) throws ResourceNotFoundException {
        Optional<ShopAdOffer> shopAdOfferOptional = shopAdOfferRepository.findById(id);
        if (shopAdOfferOptional.isPresent()) {
            return shopAdOfferOptional.get();
        } else {
            throw new ResourceNotFoundException("Shop Ad Offer not found with id: " + id);
        }
    }

    private ShopAdOffer mapShopAdOfferDTO(ShopAdOfferDTO shopAdOfferDTO) {
        ShopAdOffer shopAdOffer = new ShopAdOffer();
        shopAdOffer.setShopName(shopAdOfferDTO.getShopName());
        shopAdOffer.setCategory(shopAdOfferDTO.getCategory());
        shopAdOffer.setLocation(shopAdOfferDTO.getLocation());
        shopAdOffer.setOfferTitle(shopAdOfferDTO.getOfferTitle());
        shopAdOffer.setDescription(shopAdOfferDTO.getDescription());
        shopAdOffer.setValidUntil(shopAdOfferDTO.getValidUntil());
        shopAdOffer.setDiscountPercentage(shopAdOfferDTO.getDiscountPercentage());
        shopAdOffer.setMinPurchase(shopAdOfferDTO.getMinPurchase());
        shopAdOffer.setPromoCode(shopAdOfferDTO.getPromoCode());
        shopAdOffer.setContactInformation(shopAdOfferDTO.getContactInformation());
        return shopAdOffer;
    }
}
