package com.geethamsoft.NearByRentalService.service;

import com.geethamsoft.NearByRentalService.dto.RentalServiceDTO;
import com.geethamsoft.NearByRentalService.dto.RentalServiceSearchDTO;
import com.geethamsoft.NearByRentalService.exception.ResourceNotFoundException;
import com.geethamsoft.NearByRentalService.model.RentalService;
import com.geethamsoft.NearByRentalService.repository.RentalServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentalServiceService {
    @Autowired
    private RentalServiceRepository rentalServiceRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<RentalService> getAllRentalServices() {
        return rentalServiceRepository.findAll();
    }

    public RentalService getRentalServiceById(String id) throws ResourceNotFoundException {
        Optional<RentalService> rentalServiceOptional = rentalServiceRepository.findById(id);
        return rentalServiceOptional.orElseThrow(() -> new ResourceNotFoundException("Rental Service not found with id: " + id));
    }

    public RentalService createRentalService(RentalServiceDTO rentalServiceDTO) {
        RentalService rentalService = mapDTOToModel(rentalServiceDTO);
        return rentalServiceRepository.save(rentalService);
    }

    public RentalService updateRentalService(String id, RentalServiceDTO rentalServiceDTO) throws ResourceNotFoundException {
        RentalService existingRentalService = getRentalServiceById(id);
        RentalService updatedRentalService = mapDTOToModel(rentalServiceDTO);
        updatedRentalService.setId(existingRentalService.getId()); // Ensure the ID remains the same
        return rentalServiceRepository.save(updatedRentalService);
    }

    public void deleteRentalService(String id) throws ResourceNotFoundException {
        RentalService existingRentalService = getRentalServiceById(id);
        rentalServiceRepository.delete(existingRentalService);
    }

    public List<RentalService> searchRentalServices(RentalServiceSearchDTO searchDTO) {
        Query query = buildSearchQuery(searchDTO);
        return mongoTemplate.find(query, RentalService.class);
    }

    public Page<RentalService> getAllRentalServicesPaged(int page, int size, String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return rentalServiceRepository.findAll(pageRequest);
    }

    public Page<RentalService> searchRentalServicesPaged(RentalServiceSearchDTO searchDTO, int page, int size, String sortBy) {
        Query query = buildSearchQuery(searchDTO);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        List<RentalService> result = mongoTemplate.find(query.with(pageRequest), RentalService.class);
        return new PageImpl<>(result, pageRequest, result.size());
    }


    private Query buildSearchQuery(RentalServiceSearchDTO searchDTO) {
        Query query = new Query();

        if (searchDTO.getCategory() != null && !searchDTO.getCategory().isEmpty()) {
            query.addCriteria(Criteria.where("category").is(searchDTO.getCategory()));
        }

        if (searchDTO.getLocation() != null && !searchDTO.getLocation().isEmpty()) {
            query.addCriteria(Criteria.where("location").is(searchDTO.getLocation()));
        }

        if (searchDTO.getAvailability() != null && !searchDTO.getAvailability().isEmpty()) {
            query.addCriteria(Criteria.where("availability").is(searchDTO.getAvailability()));
        }

        if (searchDTO.getMaxPricePerHour() > 0) {
            query.addCriteria(Criteria.where("pricePerHour").lte(searchDTO.getMaxPricePerHour()));
        }

        if (searchDTO.getMaxPricePerDay() > 0) {
            query.addCriteria(Criteria.where("pricePerDay").lte(searchDTO.getMaxPricePerDay()));
        }

        if (searchDTO.getMaxPricePerUnit() > 0) {
            query.addCriteria(Criteria.where("pricePerUnit").lte(searchDTO.getMaxPricePerUnit()));
        }

        if (searchDTO.getFeatures() != null && !searchDTO.getFeatures().isEmpty()) {
            query.addCriteria(Criteria.where("features").all(searchDTO.getFeatures()));
        }

        // Add more criteria as needed

        return query;
    }

    private RentalService mapDTOToModel(RentalServiceDTO rentalServiceDTO) {
        RentalService rentalService = new RentalService();
        rentalService.setServiceName(rentalServiceDTO.getServiceName());
        rentalService.setCategory(rentalServiceDTO.getCategory());
        rentalService.setLocation(rentalServiceDTO.getLocation());
        rentalService.setContactInformation(rentalServiceDTO.getContactInformation());
        rentalService.setAvailability(rentalServiceDTO.getAvailability());
        rentalService.setDescription(rentalServiceDTO.getDescription());
        rentalService.setPricePerHour(rentalServiceDTO.getPricePerHour());
        rentalService.setPricePerDay(rentalServiceDTO.getPricePerDay());
        rentalService.setPricePerUnit(rentalServiceDTO.getPricePerUnit());
        rentalService.setUnitOfMeasurement(rentalServiceDTO.getUnitOfMeasurement());
        rentalService.setFeatures(rentalServiceDTO.getFeatures());


        return rentalService;
    }
}
