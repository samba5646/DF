package com.geethamsoft.socialworker.Service;

import com.geethamsoft.socialworker.Exception.SocialWorkerNotFoundException;
import com.geethamsoft.socialworker.Model.WorkModel;
import com.geethamsoft.socialworker.Repository.WorkRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WorkService {
    @Autowired
    private WorkRepository workRepository;
    public WorkModel addDetails(WorkModel workModel) {

        return workRepository.save(workModel);
    }

    public Optional<WorkModel> getWorkById(Long id) {
        if(workRepository.findById(id).isEmpty()){
            throw new SocialWorkerNotFoundException("Requested particular id was not found");
        }
        return workRepository.findById(id);
    }



    public List<WorkModel> getAllJobs() {

        return workRepository.findAll();
    }

    public Optional<WorkModel> updateWork(Long id, WorkModel workModel) {
        Optional<WorkModel> existingWork = workRepository.findById(id);
        if (existingWork.isPresent()) {
            WorkModel updatedService = existingWork.get();

            updatedService.setName(workModel.getName());
            updatedService.setLocation(workModel.getLocation());
            updatedService.setAvailability(workModel.getAvailability());
            updatedService.setExperience_Level(workModel.getExperience_Level());
            updatedService.setGender(workModel.getLocation());
            updatedService.setLanguage_known(workModel.getLanguage_known());
            updatedService.setSpecialisation(workModel.getSpecialisation());
            return Optional.of(workRepository.save(updatedService));
        }
        throw new SocialWorkerNotFoundException("id not found");

    }

    public void deleteWork(Long id) {
        Optional<WorkModel>existingWork = workRepository.findById(id);
        if (existingWork.isPresent()){
            workRepository.deleteById(id);
        }else{
        throw new SocialWorkerNotFoundException("details are not available in Database");
    }
  }
}
