package com.geethamsoft.NearByTechniciansAndNonTechnicians.service;

import com.geethamsoft.NearByTechniciansAndNonTechnicians.dto.NearByTechniciansAndNonTechniciansDTO;
import com.geethamsoft.NearByTechniciansAndNonTechnicians.dto.SearchNearByTechniciansAndNonTechniciansDTO;
import com.geethamsoft.NearByTechniciansAndNonTechnicians.exception.ResourceNotFoundException;
import com.geethamsoft.NearByTechniciansAndNonTechnicians.model.NearByTechniciansAndNonTechnicians;
import com.geethamsoft.NearByTechniciansAndNonTechnicians.repository.NearByTechniciansAndNonTechniciansRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NearByTechniciansAndNonTechniciansService {

    @Autowired
    private NearByTechniciansAndNonTechniciansRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public NearByTechniciansAndNonTechnicians createTechnician(NearByTechniciansAndNonTechniciansDTO dto) {
        NearByTechniciansAndNonTechnicians entity = new NearByTechniciansAndNonTechnicians();
        mapDtoToEntity(dto, entity);
        return repository.save(entity);
    }

    public NearByTechniciansAndNonTechnicians updateTechnician(String id, NearByTechniciansAndNonTechniciansDTO dto)
            throws ResourceNotFoundException {
        NearByTechniciansAndNonTechnicians existingEntity = getTechnicianById(id);
        mapDtoToEntity(dto, existingEntity);
        return repository.save(existingEntity);
    }

    public void deleteTechnician(String id) throws ResourceNotFoundException {
        NearByTechniciansAndNonTechnicians existingEntity = getTechnicianById(id);
        repository.delete(existingEntity);
    }

    public NearByTechniciansAndNonTechnicians getTechnicianById(String id) throws ResourceNotFoundException {
        Optional<NearByTechniciansAndNonTechnicians> optionalEntity = repository.findById(id);
        if (optionalEntity.isPresent()) {
            return optionalEntity.get();
        } else {
            throw new ResourceNotFoundException("Technician not found with id: " + id);
        }
    }

    public List<NearByTechniciansAndNonTechnicians> searchTechnicians(SearchNearByTechniciansAndNonTechniciansDTO searchDTO) {
        // Create a custom query using the repository's custom query methods

        // Start with a base query
        Criteria baseCriteria = new Criteria();

        // Add criteria based on searchDTO fields
        if (searchDTO.getKeyword() != null && !searchDTO.getKeyword().isEmpty()) {
            // Add keyword search criteria
            baseCriteria.andOperator(
                    Criteria.where("fullName").regex(searchDTO.getKeyword(), "i"), // Case-insensitive regex search in fullName
                    Criteria.where("jobTitle").regex(searchDTO.getKeyword(), "i")  // Case-insensitive regex search in jobTitle
            );
        }

        if (searchDTO.getRole() != null && !searchDTO.getRole().isEmpty()) {
            // Add role search criteria
            baseCriteria.and("role").is(searchDTO.getRole());
        }

        if (searchDTO.getLocation() != null && !searchDTO.getLocation().isEmpty()) {
            // Add location search criteria
            baseCriteria.and("location").is(searchDTO.getLocation());
        }

        if (searchDTO.getMinExperience() != null && searchDTO.getMaxExperience() != null) {
            // Add experience range search criteria
            baseCriteria.andOperator(
                    Criteria.where("experience").gte(searchDTO.getMinExperience()),
                    Criteria.where("experience").lte(searchDTO.getMaxExperience())
            );
        }

        if (searchDTO.getSkills() != null && !searchDTO.getSkills().isEmpty()) {
            // Add skills search criteria
            baseCriteria.and("skills").regex(searchDTO.getSkills(), "i"); // Case-insensitive regex search in skills
        }

        if (searchDTO.getLanguagesSpoken() != null && !searchDTO.getLanguagesSpoken().isEmpty()) {
            // Add languages spoken search criteria
            baseCriteria.and("languagesSpoken").in(searchDTO.getLanguagesSpoken());
        }

        if (searchDTO.getAvailability() != null && !searchDTO.getAvailability().isEmpty()) {
            // Add availability search criteria
            baseCriteria.and("availability").is(searchDTO.getAvailability());
        }

        // Sort the results based on the specified field (if provided)
        Sort sort = Sort.by(Sort.Order.desc("rating")); // Default sorting by rating (descending)

        if (searchDTO.getSortBy() != null) {
            switch (searchDTO.getSortBy()) {
                case "experienceLowToHigh":
                    sort = Sort.by(Sort.Order.asc("experience"));
                    break;
                case "experienceHighToLow":
                    sort = Sort.by(Sort.Order.desc("experience"));
                    break;
                // Add more sorting options as needed
            }
        }

        Query query = new Query(baseCriteria).with(sort);

        return mongoTemplate.find(query, NearByTechniciansAndNonTechnicians.class);
    }

    private void mapDtoToEntity(NearByTechniciansAndNonTechniciansDTO dto, NearByTechniciansAndNonTechnicians entity) {
        // Map fields from dto to entity
        entity.setFullName(dto.getFullName());
        entity.setEmail(dto.getEmail());
        entity.setMobileNumber(dto.getMobileNumber());
        entity.setLocation(dto.getLocation());
        entity.setRole(dto.getRole());
        entity.setJobTitle(dto.getJobTitle());
        entity.setExperience(dto.getExperience());
        entity.setSkills(dto.getSkills());
        entity.setBriefBio(dto.getBriefBio());
        entity.setEducation(dto.getEducation());
        entity.setLanguagesSpoken(dto.getLanguagesSpoken());
        entity.setPreferredContactMethod(dto.getPreferredContactMethod());
    }
}
