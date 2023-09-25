package com.geethamsoft.NearByBusinessOpportunity.controller;

import com.geethamsoft.NearByBusinessOpportunity.dto.BusinessListingDTO;
import com.geethamsoft.NearByBusinessOpportunity.dto.BusinessSearchDTO;
import com.geethamsoft.NearByBusinessOpportunity.exception.ResourceNotFoundException;
import com.geethamsoft.NearByBusinessOpportunity.model.BusinessListing;
import com.geethamsoft.NearByBusinessOpportunity.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/business")
public class BusinessListingController {

    @Autowired
    private BusinessService businessService;

    @GetMapping
    public List<BusinessListing> getAllBusinessListings() {
        return businessService.getAllBusinessListings();
    }

    @GetMapping("/{id}")
    public BusinessListing getBusinessListingById(@PathVariable String id) throws ResourceNotFoundException {
        return businessService.getBusinessListingById(id);
    }

    @PostMapping
    public BusinessListing createBusinessListing(@Valid @RequestBody BusinessListingDTO businessListingDTO) {
        return businessService.createBusinessListing(businessListingDTO);
    }

    @PutMapping("/{id}")
    public BusinessListing updateBusinessListing(@PathVariable String id, @Valid @RequestBody BusinessListingDTO businessListingDTO)
            throws ResourceNotFoundException {
        return businessService.updateBusinessListing(id, businessListingDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteBusinessListing(@PathVariable String id) throws ResourceNotFoundException {
        businessService.deleteBusinessListing(id);
    }

    @GetMapping("/search")
    public List<BusinessListing> searchBusinessListings(BusinessSearchDTO searchDTO) {
        return businessService.searchBusinessListings(searchDTO);
    }

    @GetMapping("/paged")
    public Page<BusinessListing> getAllBusinessListingsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return businessService.getAllBusinessListingsPaged(page, size, sortBy);
    }

    @GetMapping("/search-paged")
    public Page<BusinessListing> searchBusinessListingsPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            BusinessSearchDTO searchDTO) {
        return businessService.searchBusinessListingsPaged(searchDTO, page, size, sortBy);
    }
}

