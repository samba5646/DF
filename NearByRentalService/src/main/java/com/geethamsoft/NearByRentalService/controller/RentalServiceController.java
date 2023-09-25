package com.geethamsoft.NearByRentalService.controller;

import com.geethamsoft.NearByRentalService.dto.RentalServiceDTO;
import com.geethamsoft.NearByRentalService.dto.RentalServiceSearchDTO;
import com.geethamsoft.NearByRentalService.exception.ResourceNotFoundException;
import com.geethamsoft.NearByRentalService.model.RentalService;
import com.geethamsoft.NearByRentalService.service.RentalServiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/rental-services")
public class RentalServiceController {
    @Autowired
    private RentalServiceService rentalServiceService;

    @GetMapping
    public ResponseEntity<List<RentalService>> getAllRentalServices() {
        List<RentalService> rentalServices = rentalServiceService.getAllRentalServices();
        return new ResponseEntity<>(rentalServices, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalService> getRentalServiceById(@PathVariable String id) {
        try {
            RentalService rentalService = rentalServiceService.getRentalServiceById(id);
            return new ResponseEntity<>(rentalService, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<RentalService> createRentalService(@Valid @RequestBody RentalServiceDTO rentalServiceDTO) {
        RentalService createdRentalService = rentalServiceService.createRentalService(rentalServiceDTO);
        return new ResponseEntity<>(createdRentalService, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RentalService> updateRentalService(
            @PathVariable String id,
            @Valid @RequestBody RentalServiceDTO rentalServiceDTO) {
        try {
            RentalService updatedRentalService = rentalServiceService.updateRentalService(id, rentalServiceDTO);
            return new ResponseEntity<>(updatedRentalService, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRentalService(@PathVariable String id) {
        try {
            rentalServiceService.deleteRentalService(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<RentalService>> searchRentalServices(@ModelAttribute RentalServiceSearchDTO searchDTO) {
        List<RentalService> searchResults = rentalServiceService.searchRentalServices(searchDTO);
        return new ResponseEntity<>(searchResults, HttpStatus.OK);
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<RentalService>> getAllRentalServicesPaged(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sortBy", defaultValue = "serviceName") String sortBy) {
        Page<RentalService> rentalServices = rentalServiceService.getAllRentalServicesPaged(page, size, sortBy);
        return new ResponseEntity<>(rentalServices, HttpStatus.OK);
    }

    @GetMapping("/search-paged")
    public ResponseEntity<Page<RentalService>> searchRentalServicesPaged(
            @ModelAttribute RentalServiceSearchDTO searchDTO,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size,
            @RequestParam(name = "sortBy", defaultValue = "serviceName") String sortBy) {
        Page<RentalService> rentalServices = rentalServiceService.searchRentalServicesPaged(searchDTO, page, size, sortBy);
        return new ResponseEntity<>(rentalServices, HttpStatus.OK);
    }
}
