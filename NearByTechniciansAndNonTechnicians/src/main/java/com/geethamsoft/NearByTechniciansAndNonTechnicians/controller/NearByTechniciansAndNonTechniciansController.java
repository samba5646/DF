package com.geethamsoft.NearByTechniciansAndNonTechnicians.controller;

import com.geethamsoft.NearByTechniciansAndNonTechnicians.dto.NearByTechniciansAndNonTechniciansDTO;
import com.geethamsoft.NearByTechniciansAndNonTechnicians.dto.SearchNearByTechniciansAndNonTechniciansDTO;
import com.geethamsoft.NearByTechniciansAndNonTechnicians.exception.ResourceNotFoundException;
import com.geethamsoft.NearByTechniciansAndNonTechnicians.model.NearByTechniciansAndNonTechnicians;
import com.geethamsoft.NearByTechniciansAndNonTechnicians.service.NearByTechniciansAndNonTechniciansService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tech")
public class NearByTechniciansAndNonTechniciansController {

    @Autowired
    private NearByTechniciansAndNonTechniciansService service;

    @PostMapping
    public ResponseEntity<NearByTechniciansAndNonTechnicians> createTechnician(
            @Valid @RequestBody NearByTechniciansAndNonTechniciansDTO dto) {
        NearByTechniciansAndNonTechnicians createdTechnician = service.createTechnician(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTechnician);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getTechnicianById(@PathVariable String id) {
        try {
            NearByTechniciansAndNonTechnicians technician = service.getTechnicianById(id);
            return ResponseEntity.status(HttpStatus.OK).body(technician);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Technician not found with id: " + id);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTechnician(@PathVariable String id,
                                                   @Valid @RequestBody NearByTechniciansAndNonTechniciansDTO dto) {
        try {
            NearByTechniciansAndNonTechnicians updatedTechnician = service.updateTechnician(id, dto);
            return ResponseEntity.status(HttpStatus.OK).body(updatedTechnician);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Technician not found with id: " + id);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTechnician(@PathVariable String id) {
        try {
            service.deleteTechnician(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Technician not found with id: " + id);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<NearByTechniciansAndNonTechnicians>> searchTechnicians(
            @ModelAttribute SearchNearByTechniciansAndNonTechniciansDTO searchDTO) {
        List<NearByTechniciansAndNonTechnicians> searchResults = service.searchTechnicians(searchDTO);
        return ResponseEntity.status(HttpStatus.OK).body(searchResults);
    }
}
