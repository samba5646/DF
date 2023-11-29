package com.geethamsoft.NearbyBusinessIdeas.Controller;

import com.geethamsoft.NearbyBusinessIdeas.DTO.PostBusinessDTO;
import com.geethamsoft.NearbyBusinessIdeas.Entity.PostBusinessModel;
import com.geethamsoft.NearbyBusinessIdeas.Service.PostService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/request")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/PostBusiness/add")
    public ResponseEntity<?> addDetails(@Valid@RequestBody PostBusinessDTO postBusinessDTO) {
        PostBusinessModel CreatedBusiness = postService.addDetails(postBusinessDTO);
        return  ResponseEntity.status(HttpStatus.CREATED).body(CreatedBusiness);
    }


    @GetMapping("/PostBusiness/get")
    public ResponseEntity<List<PostBusinessModel>> getAllBusinessDetails() {
        List<PostBusinessModel> business = postService.getAllBusinessDetails();
        return new ResponseEntity<>(business, HttpStatus.OK);
    }

    @GetMapping("/PostBusiness/{id}")
    public ResponseEntity<PostBusinessModel> getBusinessDetailById(@Valid@PathVariable Long id) {
        Optional<PostBusinessModel> PostBusiness = postService.getBusinessById(id);
        return PostBusiness.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/PostBusiness/{id}")
    public ResponseEntity<PostBusinessModel> updateBusinessWork(@PathVariable Long id, @RequestBody PostBusinessDTO postBusinessDTO) {
        Optional<PostBusinessModel> updatedWork = postService.updateBusinessWork(id, postBusinessDTO);
        return updatedWork.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @DeleteMapping("/PostBusiness/{id}")
    public ResponseEntity<Void> deleteBusiness(@PathVariable Long id) {
        postService.deleteBusiness(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}