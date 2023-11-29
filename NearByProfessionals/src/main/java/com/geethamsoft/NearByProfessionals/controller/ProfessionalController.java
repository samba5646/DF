package com.geethamsoft.NearByProfessionals.controller;

import com.geethamsoft.NearByProfessionals.DTO.ProfessionalDTO;
import com.geethamsoft.NearByProfessionals.DTO.ProfessionalSearchDTO;
import com.geethamsoft.NearByProfessionals.model.Professional;
import com.geethamsoft.NearByProfessionals.service.ProfessionalService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<Professional> registerProfessional(@Valid @RequestBody ProfessionalDTO professionalDTO) {
        Professional professional = professionalService.registerProfessional(professionalDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(professional);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProfessional(@PathVariable String id,@Valid @RequestBody ProfessionalDTO professionalDTO) {
        Optional<Professional> optionalProfessional =professionalService.updateProfessional(id,professionalDTO);
            return optionalProfessional.map(value-> new ResponseEntity<>(value , HttpStatus.OK))
                    .orElseGet(()-> new ResponseEntity<>(HttpStatus.NOT_FOUND));

        }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProfessional(@PathVariable String id) {

            professionalService.deleteProfessional(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }
    }



