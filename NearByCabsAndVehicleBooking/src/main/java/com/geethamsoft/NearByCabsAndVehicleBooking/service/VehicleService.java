package com.geethamsoft.NearByCabsAndVehicleBooking.service;

import com.geethamsoft.NearByCabsAndVehicleBooking.dto.VehicleDTO;
import com.geethamsoft.NearByCabsAndVehicleBooking.dto.VehicleSearchDTO;
import com.geethamsoft.NearByCabsAndVehicleBooking.exception.ResourceNotFoundException;
import com.geethamsoft.NearByCabsAndVehicleBooking.model.Vehicle;
import com.geethamsoft.NearByCabsAndVehicleBooking.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public List<Vehicle> searchVehicles(VehicleSearchDTO searchDTO) {
        Query query = buildSearchQuery(searchDTO);
        return mongoTemplate.find(query, Vehicle.class);
    }

    private Query buildSearchQuery(VehicleSearchDTO searchDTO) {
        Query query = new Query();

        if (searchDTO.getLocation() != null && !searchDTO.getLocation().isEmpty()) {
            query.addCriteria(Criteria.where("location").is(searchDTO.getLocation()));
        }

        if (searchDTO.getVehicleType() != null && !searchDTO.getVehicleType().isEmpty()) {
            query.addCriteria(Criteria.where("vehicleType").is(searchDTO.getVehicleType()));
        }

        if (searchDTO.getTransmission() != null && !searchDTO.getTransmission().isEmpty()) {
            query.addCriteria(Criteria.where("transmission").is(searchDTO.getTransmission()));
        }

        if (searchDTO.getFuelType() != null && !searchDTO.getFuelType().isEmpty()) {
            query.addCriteria(Criteria.where("fuelType").is(searchDTO.getFuelType()));
        }

        if (searchDTO.getMinSeats() != null) {
            query.addCriteria(Criteria.where("numberOfSeats").gte(searchDTO.getMinSeats()));
        }

        if (searchDTO.getMaxPrice() != null) {
            query.addCriteria(Criteria.where("pricePerHour").lte(searchDTO.getMaxPrice()));
        }

        if (searchDTO.getAvailability() != null && !searchDTO.getAvailability().isEmpty()) {
            query.addCriteria(Criteria.where("availability").is(searchDTO.getAvailability()));
        }

        // Add more criteria for other fields as needed

        return query;
    }

    public Vehicle addVehicle(VehicleDTO vehicleDTO, MultipartFile[] images, MultipartFile licenseFile)
            throws IOException {
        Vehicle vehicle = mapVehicleDTO(vehicleDTO);

        // Handle image and license file uploads here
        // Save the vehicle and related files to the database

        // For example, you can store images and license file as byte arrays in the Vehicle object
        // vehicle.setVehicleImages(images);
        // vehicle.setLicense(licenseFile.getBytes());

        return vehicleRepository.save(vehicle);
    }

    public Vehicle updateVehicle(String id, VehicleDTO vehicleDTO, MultipartFile[] images, MultipartFile licenseFile)
            throws ResourceNotFoundException, IOException {
        Vehicle existingVehicle = getVehicleById(id);
        Vehicle updatedVehicle = mapVehicleDTO(vehicleDTO);

        // Copy updated fields to the existing vehicle
        existingVehicle.setVehicleName(updatedVehicle.getVehicleName());
        existingVehicle.setVehicleType(updatedVehicle.getVehicleType());
        existingVehicle.setNumberOfSeats(updatedVehicle.getNumberOfSeats());
        existingVehicle.setModelYear(updatedVehicle.getModelYear());
        existingVehicle.setLicensePlate(updatedVehicle.getLicensePlate());
        existingVehicle.setContactInformation(updatedVehicle.getContactInformation());
        existingVehicle.setAvailability(updatedVehicle.getAvailability());
        existingVehicle.setDescription(updatedVehicle.getDescription());
        existingVehicle.setPricePerHour(updatedVehicle.getPricePerHour());
        existingVehicle.setPricePerDay(updatedVehicle.getPricePerDay());
        existingVehicle.setPricePerKilometer(updatedVehicle.getPricePerKilometer());
        existingVehicle.setTransmission(updatedVehicle.getTransmission());
        existingVehicle.setFuelType(updatedVehicle.getFuelType());
        existingVehicle.setFeatures(updatedVehicle.getFeatures());

        // Handle image and license file updates here (if needed)
        // if (images != null && images.length > 0) {
        //     // Update vehicle images
        //     existingVehicle.setImages(images);
        // }
        // if (licenseFile != null) {
        //     // Update license file
        //     existingVehicle.setLicense(licenseFile.getBytes());
        // }

        return vehicleRepository.save(existingVehicle);
    }

    public void deleteVehicle(String id) throws ResourceNotFoundException {
        Vehicle existingVehicle = getVehicleById(id);
        vehicleRepository.delete(existingVehicle);
    }

    public Vehicle getVehicleById(String id) throws ResourceNotFoundException {
        Optional<Vehicle> vehicleOptional = vehicleRepository.findById(id);
        if (vehicleOptional.isPresent()) {
            return vehicleOptional.get();
        } else {
            throw new ResourceNotFoundException("Vehicle not found with id: " + id);
        }
    }

    private Vehicle mapVehicleDTO(VehicleDTO vehicleDTO) {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleName(vehicleDTO.getVehicleName());
        vehicle.setVehicleType(vehicleDTO.getVehicleType());
        vehicle.setNumberOfSeats(vehicleDTO.getNumberOfSeats());
        vehicle.setModelYear(vehicleDTO.getModelYear());
        vehicle.setLicensePlate(vehicleDTO.getLicensePlate());
        vehicle.setContactInformation(vehicleDTO.getContactInformation());
        vehicle.setAvailability(vehicleDTO.getAvailability());
        vehicle.setDescription(vehicleDTO.getDescription());
        vehicle.setPricePerHour(vehicleDTO.getPricePerHour());
        vehicle.setPricePerDay(vehicleDTO.getPricePerDay());
        vehicle.setPricePerKilometer(vehicleDTO.getPricePerKilometer());
        vehicle.setTransmission(vehicleDTO.getTransmission());
        vehicle.setFuelType(vehicleDTO.getFuelType());
        vehicle.setFeatures(vehicleDTO.getFeatures().toArray(new String[0]));

        // You may need to map other fields from VehicleDTO to Vehicle as needed.

        return vehicle;
    }
}
