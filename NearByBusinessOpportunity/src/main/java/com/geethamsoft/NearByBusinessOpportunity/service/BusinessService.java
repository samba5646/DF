package com.geethamsoft.NearByBusinessOpportunity.service;

import com.geethamsoft.NearByBusinessOpportunity.dto.BusinessListingDTO;
import com.geethamsoft.NearByBusinessOpportunity.dto.BusinessSearchDTO;
import com.geethamsoft.NearByBusinessOpportunity.exception.ResourceNotFoundException;
import com.geethamsoft.NearByBusinessOpportunity.model.BusinessListing;
import com.geethamsoft.NearByBusinessOpportunity.repository.BusinessListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusinessService {
    @Autowired
    private BusinessListingRepository businessListingRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<BusinessListing> getAllBusinessListings() {
        return businessListingRepository.findAll();
    }

    public BusinessListing getBusinessListingById(String id) throws ResourceNotFoundException {
        Optional<BusinessListing> businessListingOptional = businessListingRepository.findById(id);
        if (businessListingOptional.isPresent()) {
            return businessListingOptional.get();
        } else {
            throw new ResourceNotFoundException("Business Listing not found with id: " + id);
        }
    }

    public BusinessListing createBusinessListing(BusinessListingDTO businessListingDTO) {
        BusinessListing businessListing = mapDTOToModel(businessListingDTO);
        return businessListingRepository.save(businessListing);
    }

    public BusinessListing updateBusinessListing(String id, BusinessListingDTO businessListingDTO) throws ResourceNotFoundException {
        BusinessListing existingBusinessListing = getBusinessListingById(id);
        BusinessListing updatedBusinessListing = mapDTOToModel(businessListingDTO);
        updatedBusinessListing.setId(existingBusinessListing.getId()); // Ensure the ID remains the same
        return businessListingRepository.save(updatedBusinessListing);
    }

    public void deleteBusinessListing(String id) throws ResourceNotFoundException {
        BusinessListing existingBusinessListing = getBusinessListingById(id);
        businessListingRepository.delete(existingBusinessListing);
    }

    public List<BusinessListing> searchBusinessListings(BusinessSearchDTO searchDTO) {
        Query query = buildSearchQuery(searchDTO);
        return mongoTemplate.find(query, BusinessListing.class);
    }

    public Page<BusinessListing> getAllBusinessListingsPaged(int page, int size, String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return businessListingRepository.findAll(pageRequest);
    }

    public Page<BusinessListing> searchBusinessListingsPaged(BusinessSearchDTO searchDTO, int page, int size, String sortBy) {
        Query query = buildSearchQuery(searchDTO);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        List<BusinessListing> result = mongoTemplate.find(query.with(pageRequest), BusinessListing.class);
        return Page.empty();
    }


    private Query buildSearchQuery(BusinessSearchDTO searchDTO) {
        Query query = new Query();

        if (searchDTO.getSearchKeyword() != null && !searchDTO.getSearchKeyword().isEmpty()) {
            query.addCriteria(Criteria.where("businessName").regex(searchDTO.getSearchKeyword(), "i")); // Case-insensitive keyword search in the businessName field
        }

        if (searchDTO.getCategory() != null && !searchDTO.getCategory().isEmpty()) {
            query.addCriteria(Criteria.where("category").is(searchDTO.getCategory()));
        }

        if (searchDTO.getLocation() != null && !searchDTO.getLocation().isEmpty()) {
            query.addCriteria(Criteria.where("location").regex(searchDTO.getLocation(), "i")); // Case-insensitive location search
        }

        if (searchDTO.getMinInvestment() >= 0 && searchDTO.getMaxInvestment() >= searchDTO.getMinInvestment()) {
            query.addCriteria(Criteria.where("minInvestment").gte(searchDTO.getMinInvestment()).lte(searchDTO.getMaxInvestment()));
        }



        if (searchDTO.getRequiredSkills() != null && !searchDTO.getRequiredSkills().isEmpty()) {
            query.addCriteria(Criteria.where("requiredSkills").regex(searchDTO.getRequiredSkills(), "i")); // Case-insensitive skill search
        }

        if (searchDTO.getBusinessType() != null && !searchDTO.getBusinessType().isEmpty()) {
            query.addCriteria(Criteria.where("businessType").is(searchDTO.getBusinessType()));
        }

        if (searchDTO.getBusinessIndustry() != null && !searchDTO.getBusinessIndustry().isEmpty()) {
            query.addCriteria(Criteria.where("businessIndustry").is(searchDTO.getBusinessIndustry()));
        }

        // Add more criteria as needed

        return query;
    }

    private BusinessListing mapDTOToModel(BusinessListingDTO businessListingDTO) {
        BusinessListing businessListing = new BusinessListing();
        businessListing.setBusinessName(businessListingDTO.getBusinessName());
        businessListing.setEmail(businessListingDTO.getEmail());
        businessListing.setContactNumber(businessListingDTO.getContactNumber());
        businessListing.setLocation(businessListingDTO.getLocation());
        businessListing.setCategory(businessListingDTO.getCategory());
        businessListing.setMinInvestment(businessListingDTO.getMinInvestment());
        businessListing.setMaxInvestment(businessListingDTO.getMaxInvestment());
        businessListing.setRequiredSkills(businessListingDTO.getRequiredSkills());
        businessListing.setBusinessType(businessListingDTO.getBusinessType());
        businessListing.setBusinessIndustry(businessListingDTO.getBusinessIndustry());
        businessListing.setDescription(businessListingDTO.getDescription());
        businessListing.setRating(businessListingDTO.getRating());
        businessListing.setLanguagesSpoken(businessListingDTO.getLanguagesSpoken());
        businessListing.setPreferredContactMethod(businessListingDTO.getPreferredContactMethod());
        businessListing.setWebsiteUrl(businessListingDTO.getWebsiteUrl());
        businessListing.setSocialMediaLinks(businessListingDTO.getSocialMediaLinks());
        businessListing.setBusinessLogoUrl(businessListingDTO.getBusinessLogoUrl());
        businessListing.setBusinessImagesUrls(businessListingDTO.getBusinessImagesUrls());
        businessListing.setVideosUrls(businessListingDTO.getVideosUrls());
        businessListing.setAdditionalDocumentsUrls(businessListingDTO.getAdditionalDocumentsUrls());
        businessListing.setPaymentMethods(businessListingDTO.getPaymentMethods());
        businessListing.setWorkingHours(businessListingDTO.getWorkingHours());
        businessListing.setCustomerReviews(businessListingDTO.getCustomerReviews());
        return businessListing;
    }
}
