package com.geethamsoft.SocialWorker.Controller;

import com.geethamsoft.SocialWorker.DTO.WorkModelDto;
import com.geethamsoft.SocialWorker.Model.WorkModel;
import com.geethamsoft.SocialWorker.Service.WorkService;
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
public class WorkController {
    @Autowired
    private WorkService workService;
    @PostMapping( "/work/add")
    public ResponseEntity<?> addDetails(@Valid @RequestBody WorkModelDto workModelDto){
        WorkModel CreatedWork =workService.addDetails(workModelDto);
        return new ResponseEntity<>(CreatedWork,HttpStatus.CREATED);
    }




    @GetMapping  ("/work/{id}")
    public ResponseEntity<WorkModel> getWorkById(@PathVariable Long id) {
        Optional<WorkModel> work = workService.getWorkById(id);
            return work.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
    @GetMapping("/work/get")
    public ResponseEntity<List<WorkModel>> getAllJobs() {
        List<WorkModel> jobs = workService.getAllJobs();
        return new ResponseEntity<>(jobs, HttpStatus.OK);
    }
    @PutMapping("/work/{id}")
    public ResponseEntity<WorkModel> updateWork(@PathVariable Long id, @RequestBody WorkModelDto workModelDto) {
        Optional<WorkModel> updatedWork = workService.updateWork(id, workModelDto);
        return updatedWork.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
//
    @DeleteMapping("/job/{id}")
    public ResponseEntity<Void> deleteWork(@PathVariable Long id) {
        workService.deleteWork(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }



}




