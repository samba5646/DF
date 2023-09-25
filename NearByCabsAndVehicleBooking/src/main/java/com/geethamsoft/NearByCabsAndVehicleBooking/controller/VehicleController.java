package com.geethamsoft.NearByCabsAndVehicleBooking.controller;

import com.geethamsoft.NearByCabsAndVehicleBooking.dto.VehicleDTO;
import com.geethamsoft.NearByCabsAndVehicleBooking.dto.VehicleSearchDTO;
import com.geethamsoft.NearByCabsAndVehicleBooking.exception.ResourceNotFoundException;
import com.geethamsoft.NearByCabsAndVehicleBooking.model.Vehicle;
import com.geethamsoft.NearByCabsAndVehicleBooking.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {
    @Autowired
    private VehicleService vehicleService;

    @GetMapping
    public List<Vehicle> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getVehicleById(@PathVariable String id) {
        try {
            Vehicle vehicle = vehicleService.getVehicleById(id);
            return new ResponseEntity<>(vehicle, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>("Vehicle not found with id: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<?> createVehicle(
            @Valid @RequestBody VehicleDTO vehicleDTO,
            @RequestParam(required = false) MultipartFile[] images,
            @RequestParam(required = false) MultipartFile licenseFile) {
        try {
            Vehicle createdVehicle = vehicleService.addVehicle(vehicleDTO, images, licenseFile);
            return new ResponseEntity<>(createdVehicle, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload images or license file.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVehicle(
            @PathVariable String id,
            @Valid @RequestBody VehicleDTO vehicleDTO,
            @RequestParam(required = false) MultipartFile[] images,
            @RequestParam(required = false) MultipartFile licenseFile) {
        try {
            Vehicle updatedVehicle = vehicleService.updateVehicle(id, vehicleDTO, images, licenseFile);
            return new ResponseEntity<>(updatedVehicle, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>("Vehicle not found with id: " + id, HttpStatus.NOT_FOUND);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload images or license file.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVehicle(@PathVariable String id) {
        try {
            vehicleService.deleteVehicle(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>("Vehicle not found with id: " + id, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Vehicle>> searchVehicles(@ModelAttribute VehicleSearchDTO searchDTO) {
        List<Vehicle> searchResults = vehicleService.searchVehicles(searchDTO);
        return new ResponseEntity<>(searchResults, HttpStatus.OK);
    }
}
