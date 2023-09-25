package com.geethamsoft.NearByBloodDonor.service;

import com.geethamsoft.NearByBloodDonor.dto.BloodDonorDTO;
import com.geethamsoft.NearByBloodDonor.dto.BloodDonorSearchDTO;
import com.geethamsoft.NearByBloodDonor.exception.ResourceNotFoundException;
import com.geethamsoft.NearByBloodDonor.model.BloodDonor;
import com.geethamsoft.NearByBloodDonor.repository.BloodDonorRepository;
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
public class BloodDonorService {
    @Autowired
    private BloodDonorRepository bloodDonorRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<BloodDonor> getAllBloodDonors() {
        return bloodDonorRepository.findAll();
    }

    public BloodDonor getBloodDonorById(String id) throws ResourceNotFoundException {
        Optional<BloodDonor> bloodDonorOptional = bloodDonorRepository.findById(id);
        if (bloodDonorOptional.isPresent()) {
            return bloodDonorOptional.get();
        } else {
            throw new ResourceNotFoundException("Blood Donor not found with id: " + id);
        }
    }

    public BloodDonor createBloodDonor(BloodDonorDTO bloodDonorDTO) {
        BloodDonor bloodDonor = mapDTOToModel(bloodDonorDTO);
        return bloodDonorRepository.save(bloodDonor);
    }

    public BloodDonor updateBloodDonor(String id, BloodDonorDTO bloodDonorDTO) throws ResourceNotFoundException {
        BloodDonor existingBloodDonor = getBloodDonorById(id);
        BloodDonor updatedBloodDonor = mapDTOToModel(bloodDonorDTO);
        updatedBloodDonor.setId(existingBloodDonor.getId()); // Ensure the ID remains the same
        return bloodDonorRepository.save(updatedBloodDonor);
    }

    public void deleteBloodDonor(String id) throws ResourceNotFoundException {
        BloodDonor existingBloodDonor = getBloodDonorById(id);
        bloodDonorRepository.delete(existingBloodDonor);
    }

    public List<BloodDonor> searchBloodDonors(BloodDonorSearchDTO searchDTO) {
        Query query = buildSearchQuery(searchDTO);
        return mongoTemplate.find(query, BloodDonor.class);
    }


    public Page<BloodDonor> getAllBloodDonorsPaged(int page, int size, String sortBy) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return bloodDonorRepository.findAll(pageRequest);
    }

    public Page<BloodDonor> searchBloodDonorsPaged(BloodDonorSearchDTO searchDTO, int page, int size, String sortBy) {
        Query query = buildSearchQuery(searchDTO);
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        List<BloodDonor> result = mongoTemplate.find(query.with(pageRequest), BloodDonor.class);
        return Page.empty();
    }

    private Query buildSearchQuery(BloodDonorSearchDTO searchDTO) {
        Query query = new Query();

        if (searchDTO.getBloodType() != null && !searchDTO.getBloodType().isEmpty()) {
            query.addCriteria(Criteria.where("bloodType").is(searchDTO.getBloodType()));
        }

        if (searchDTO.getLocation() != null && !searchDTO.getLocation().isEmpty()) {
            query.addCriteria(Criteria.where("location").regex(searchDTO.getLocation(), "i"));
        }

        if (searchDTO.getAvailability() != null && !searchDTO.getAvailability().isEmpty()) {
            query.addCriteria(Criteria.where("availability").is(searchDTO.getAvailability()));
        }

        if (searchDTO.getLastDonationDate() != null) {
            query.addCriteria(Criteria.where("lastDonationDate").gte(searchDTO.getLastDonationDate()));
        }

        if (searchDTO.getMinAge() != null && searchDTO.getMaxAge() != null && searchDTO.getMinAge() >= 0 && searchDTO.getMaxAge() >= searchDTO.getMinAge()) {
            query.addCriteria(Criteria.where("age").gte(searchDTO.getMinAge()).lte(searchDTO.getMaxAge()));
        }

        if (searchDTO.getGender() != null && !searchDTO.getGender().isEmpty()) {
            query.addCriteria(Criteria.where("gender").is(searchDTO.getGender()));
        }

        if (searchDTO.getSmokingHabit() != null && !searchDTO.getSmokingHabit().isEmpty()) {
            query.addCriteria(Criteria.where("smokingHabit").is(searchDTO.getSmokingHabit()));
        }

        if (searchDTO.getDrinkingHabit() != null && !searchDTO.getDrinkingHabit().isEmpty()) {
            query.addCriteria(Criteria.where("drinkingHabit").is(searchDTO.getDrinkingHabit()));
        }

        if (searchDTO.getDonationCost() != null && !searchDTO.getDonationCost().isEmpty()) {
            query.addCriteria(Criteria.where("donationCost").is(searchDTO.getDonationCost()));
        }

        if (searchDTO.getCostRangeMin() != null && searchDTO.getCostRangeMax() != null && searchDTO.getCostRangeMin() >= 0 && searchDTO.getCostRangeMax() >= searchDTO.getCostRangeMin()) {
            query.addCriteria(Criteria.where("donationAmount").gte(searchDTO.getCostRangeMin()).lte(searchDTO.getCostRangeMax()));
        }

        // Add more criteria as needed

        return query;
    }

    private BloodDonor mapDTOToModel(BloodDonorDTO bloodDonorDTO) {
        BloodDonor bloodDonor = new BloodDonor();
        bloodDonor.setName(bloodDonorDTO.getName());
        bloodDonor.setBloodType(bloodDonorDTO.getBloodType());
        bloodDonor.setLocation(bloodDonorDTO.getLocation());
        bloodDonor.setAvailability(bloodDonorDTO.getAvailability());
        bloodDonor.setLastDonationDate(bloodDonorDTO.getLastDonationDate());
        bloodDonor.setAge(bloodDonorDTO.getAge());
        bloodDonor.setGender(bloodDonorDTO.getGender());
        bloodDonor.setSmokingHabit(bloodDonorDTO.getSmokingHabit());
        bloodDonor.setDrinkingHabit(bloodDonorDTO.getDrinkingHabit());
        bloodDonor.setDonationCost(bloodDonorDTO.getDonationCost());
        bloodDonor.setDonationAmount(bloodDonorDTO.getDonationAmount());
        bloodDonor.setContactInformation(bloodDonorDTO.getContactInformation());
        return bloodDonor;
    }

}

