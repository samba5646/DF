package com.geethamsoft.NearByBuyAndSellProducts.repository;

import com.geethamsoft.NearByBuyAndSellProducts.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {

}
