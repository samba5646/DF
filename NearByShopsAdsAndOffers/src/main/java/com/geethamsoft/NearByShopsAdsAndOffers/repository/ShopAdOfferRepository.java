package com.geethamsoft.NearByShopsAdsAndOffers.repository;


import com.geethamsoft.NearByShopsAdsAndOffers.model.ShopAdOffer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShopAdOfferRepository extends MongoRepository<ShopAdOffer, String> {
}

