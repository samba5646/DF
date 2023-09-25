package com.geethamsoft.NearByBusinessOpportunity.repository;
import com.geethamsoft.NearByBusinessOpportunity.model.BusinessListing;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessListingRepository extends MongoRepository<BusinessListing, String> {
}
