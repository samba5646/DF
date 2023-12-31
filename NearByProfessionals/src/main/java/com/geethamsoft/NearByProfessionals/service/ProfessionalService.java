package com.geethamsoft.NearByProfessionals.service;

import com.geethamsoft.NearByProfessionals.DTO.ProfessionalDTO;
import com.geethamsoft.NearByProfessionals.DTO.ProfessionalSearchDTO;
import com.geethamsoft.NearByProfessionals.exception.ProfessionalNotFoundException;
import com.geethamsoft.NearByProfessionals.model.Professional;
import com.geethamsoft.NearByProfessionals.repository.ProfessionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessionalService {
    @Autowired
    private ProfessionalRepository professionalRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Professional> getAllProfessionals() {
        return professionalRepository.findAll();
    }

    public List<Professional> searchProfessionals(ProfessionalSearchDTO searchDTO) {
        Query query = buildSearchQuery(searchDTO);
        return mongoTemplate.find(query, Professional.class);
    }

    private Query buildSearchQuery(ProfessionalSearchDTO searchDTO) {
        Query query = new Query();

        // Text search filter (search by keyword)
        if (searchDTO.getKeyword() != null && !searchDTO.getKeyword().isEmpty()) {
            Criteria keywordCriteria = new Criteria().orOperator(
                    Criteria.where("fullName").regex(searchDTO.getKeyword(), "i"),
                    Criteria.where("jobTitleOrSpecialization").regex(searchDTO.getKeyword(), "i"),
                    Criteria.where("briefBio").regex(searchDTO.getKeyword(), "i"),
                    Criteria.where("education").regex(searchDTO.getKeyword(), "i")
            );
            query.addCriteria(keywordCriteria);
        }

        // Role filter
        if (searchDTO.getType() != null && !searchDTO.getType().isEmpty()) {
            query.addCriteria(Criteria.where("type").is(searchDTO.getType()));
        }

        // Location filter
        if (searchDTO.getLocation() != null && !searchDTO.getLocation().isEmpty()) {
            query.addCriteria(Criteria.where("location").is(searchDTO.getLocation()));
        }

        // Experience range filter
        if (searchDTO.getMinExperience() > 0) {
            query.addCriteria(Criteria.where("experience").gte(searchDTO.getMinExperience()));
        }

        if (searchDTO.getMaxExperience() > 0) {
            query.addCriteria(Criteria.where("experience").lte(searchDTO.getMaxExperience()));
        }

        // Skills filter
        if (searchDTO.getSkills() != null && !searchDTO.getSkills().isEmpty()) {
            query.addCriteria(Criteria.where("skills").regex(searchDTO.getSkills(), "i"));
        }

        // Languages Spoken filter
        if (searchDTO.getLanguagesSpoken() != null && !searchDTO.getLanguagesSpoken().isEmpty()) {
            query.addCriteria(Criteria.where("languagesSpoken").regex(searchDTO.getLanguagesSpoken(), "i"));
        }

        // Availability filter
        if (searchDTO.getAvailability() != null && !searchDTO.getAvailability().isEmpty()) {
            query.addCriteria(Criteria.where("availability").is(searchDTO.getAvailability()));
        }

        // Preferred Contact Method filter
        if (searchDTO.getPreferredContactMethod() != null && !searchDTO.getPreferredContactMethod().isEmpty()) {
            query.addCriteria(Criteria.where("preferredContactMethod").is(searchDTO.getPreferredContactMethod()));
        }

        // Price Range filter
        if (searchDTO.getMinPrice() > 0 || searchDTO.getMaxPrice() > 0) {
            Criteria priceCriteria = new Criteria();
            if (searchDTO.getMinPrice() > 0) {
                priceCriteria = priceCriteria.andOperator(Criteria.where("minPrice").gte(searchDTO.getMinPrice()));
            }
            if (searchDTO.getMaxPrice() > 0) {
                priceCriteria = priceCriteria.andOperator(Criteria.where("maxPrice").lte(searchDTO.getMaxPrice()));
            }
            query.addCriteria(priceCriteria);
        }

        return query;
    }

    public Professional registerProfessional(ProfessionalDTO professionalDTO) {
        Professional professional = mapProfessionalDTOToProfessional(professionalDTO);
        return professionalRepository.save(professional);}



    public Optional<Professional> updateProfessional(String id, ProfessionalDTO professionalDTO) {
        Optional<Professional> existingProfessional = professionalRepository.findById(id);
        if (existingProfessional.isPresent()) {
            Professional updatedProfessional =mapProfessionalDTOToProfessional(professionalDTO);
            updatedProfessional.setFullName(professionalDTO.getFullName());
            updatedProfessional.setEmail(professionalDTO.getEmail());
            updatedProfessional.setMobileNumber(professionalDTO.getMobileNumber());
            updatedProfessional.setLocation(professionalDTO.getLocation());
            updatedProfessional.setType(professionalDTO.getType());
            updatedProfessional.setJobTitleOrSpecialization(professionalDTO.getJobTitleOrSpecialization());
            updatedProfessional.setExperience(professionalDTO.getExperience());
            updatedProfessional.setSkills(professionalDTO.getSkills());
            updatedProfessional.setLanguagesSpoken(professionalDTO.getLanguagesSpoken());
            updatedProfessional.setBriefBio(professionalDTO.getBriefBio());
            updatedProfessional.setEducation(professionalDTO.getEducation());
            updatedProfessional.setAvailability(professionalDTO.getAvailability());
            updatedProfessional.setPreferredContactMethod(professionalDTO.getPreferredContactMethod());
            updatedProfessional.setMinPrice(professionalDTO.getMinPrice());
            updatedProfessional.setMaxPrice(professionalDTO.getMaxPrice());
            return Optional.of(professionalRepository.save(updatedProfessional));
        }
        throw new ProfessionalNotFoundException("Professional not found with id: " + id);
    }


    public void deleteProfessional(String id) throws ProfessionalNotFoundException {
        Professional existingProfessional = professionalRepository.findById(id)
                .orElseThrow(() -> new ProfessionalNotFoundException("Professional not found with id: " + id));

        professionalRepository.delete(existingProfessional);
    }

    private Professional mapProfessionalDTOToProfessional(ProfessionalDTO professionalDTO) {
        Professional professional =new Professional();
        professional.setFullName(professionalDTO.getFullName());
        professional.setEmail(professionalDTO.getEmail());
        professional.setMobileNumber(professionalDTO.getMobileNumber());
        professional.setLocation(professionalDTO.getLocation());
        professional.setType(professionalDTO.getType());
        professional.setJobTitleOrSpecialization(professionalDTO.getJobTitleOrSpecialization());
        professional.setExperience(professionalDTO.getExperience());
        professional.setSkills(professionalDTO.getSkills());
        professional.setLanguagesSpoken(professionalDTO.getLanguagesSpoken());
        professional.setBriefBio(professionalDTO.getBriefBio());
        professional.setEducation(professionalDTO.getEducation());
        professional.setAvailability(professionalDTO.getAvailability());
        professional.setPreferredContactMethod(professionalDTO.getPreferredContactMethod());
        professional.setMinPrice(professionalDTO.getMinPrice());
        professional.setMaxPrice(professionalDTO.getMaxPrice());
        return professional;
    }


}
