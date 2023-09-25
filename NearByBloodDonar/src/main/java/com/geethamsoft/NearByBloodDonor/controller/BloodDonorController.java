package com.geethamsoft.NearByBloodDonor.controller;


import com.geethamsoft.NearByBloodDonor.dto.BloodDonorDTO;
import com.geethamsoft.NearByBloodDonor.dto.BloodDonorSearchDTO;
import com.geethamsoft.NearByBloodDonor.exception.ResourceNotFoundException;
import com.geethamsoft.NearByBloodDonor.model.BloodDonor;
import com.geethamsoft.NearByBloodDonor.service.BloodDonorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blooddonors")
public class BloodDonorController {

    @Autowired
    private BloodDonorService bloodDonorService;

    @GetMapping
    public ResponseEntity<List<BloodDonor>> getAllBloodDonors() {
        List<BloodDonor> bloodDonors = bloodDonorService.getAllBloodDonors();
        return ResponseEntity.ok(bloodDonors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BloodDonor> getBloodDonorById(@PathVariable String id) throws ResourceNotFoundException {
        BloodDonor bloodDonor = bloodDonorService.getBloodDonorById(id);
        return ResponseEntity.ok(bloodDonor);
    }

    @PostMapping
    public ResponseEntity<BloodDonor> createBloodDonor(@Valid @RequestBody BloodDonorDTO bloodDonorDTO) {
        BloodDonor bloodDonor = bloodDonorService.createBloodDonor(bloodDonorDTO);
        return new ResponseEntity<>(bloodDonor, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BloodDonor> updateBloodDonor(@PathVariable String id, @Valid @RequestBody BloodDonorDTO bloodDonorDTO) throws ResourceNotFoundException {
        BloodDonor updatedBloodDonor = bloodDonorService.updateBloodDonor(id, bloodDonorDTO);
        return ResponseEntity.ok(updatedBloodDonor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBloodDonor(@PathVariable String id) throws ResourceNotFoundException {
        bloodDonorService.deleteBloodDonor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<BloodDonor>> searchBloodDonors(BloodDonorSearchDTO searchDTO) {
        List<BloodDonor> bloodDonors = bloodDonorService.searchBloodDonors(searchDTO);
        return ResponseEntity.ok(bloodDonors);
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<BloodDonor>> getAllBloodDonorsPaged(@RequestParam(name = "page", defaultValue = "0") int page,
                                                                   @RequestParam(name = "size", defaultValue = "10") int size,
                                                                   @RequestParam(name = "sortBy", defaultValue = "name") String sortBy) {
        Page<BloodDonor> bloodDonors = bloodDonorService.getAllBloodDonorsPaged(page, size, sortBy);
        return ResponseEntity.ok(bloodDonors);
    }

    @GetMapping("/search/paged")
    public ResponseEntity<Page<BloodDonor>> searchBloodDonorsPaged(BloodDonorSearchDTO searchDTO,
                                                                   @RequestParam(name = "page", defaultValue = "0") int page,
                                                                   @RequestParam(name = "size", defaultValue = "10") int size,
                                                                   @RequestParam(name = "sortBy", defaultValue = "name") String sortBy) {
        Page<BloodDonor> bloodDonors = bloodDonorService.searchBloodDonorsPaged(searchDTO, page, size, sortBy);
        return ResponseEntity.ok(bloodDonors);
    }
}

