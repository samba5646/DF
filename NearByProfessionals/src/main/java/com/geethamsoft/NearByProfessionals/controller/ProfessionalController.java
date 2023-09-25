package com.geethamsoft.NearByProfessionals.controller;

import com.geethamsoft.NearByProfessionals.DTO.ProfessionalDTO;
import com.geethamsoft.NearByProfessionals.DTO.ProfessionalSearchDTO;
import com.geethamsoft.NearByProfessionals.exception.ResourceNotFoundException;
import com.geethamsoft.NearByProfessionals.model.Professional;
import com.geethamsoft.NearByProfessionals.service.ProfessionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/professional")
public class ProfessionalController {
    @Autowired
    private ProfessionalService professionalService;

    @GetMapping
    public ResponseEntity<List<Professional>> getAllProfessionals() {
        List<Professional> professionals = professionalService.getAllProfessionals();
        return ResponseEntity.ok(professionals);
    }

    @PostMapping("/search")
    public ResponseEntity<List<Professional>> searchProfessionals(@RequestBody ProfessionalSearchDTO searchDTO) {
        List<Professional> professionals = professionalService.searchProfessionals(searchDTO);
        return ResponseEntity.ok(professionals);
    }

    @PostMapping("/register")
    public ResponseEntity<Professional> registerProfessional(@RequestBody ProfessionalDTO professionalDTO) {
        Professional professional = professionalService.registerProfessional(professionalDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(professional);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfessional(@PathVariable String id, @RequestBody ProfessionalDTO professionalDTO) {
        try {
            Professional professional = professionalService.updateProfessional(id, professionalDTO);
            return ResponseEntity.ok(professional);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Resource not found with id: " + id));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProfessional(@PathVariable String id) {
        try {
            professionalService.deleteProfessional(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Resource not found with id: " + id));
        }
    }

    // Define an ErrorResponse class for handling exceptions
    static class ErrorResponse {
        private String message;

        public ErrorResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
