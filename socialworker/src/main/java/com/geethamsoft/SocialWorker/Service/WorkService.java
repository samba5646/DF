package com.geethamsoft.SocialWorker.Service;

import com.geethamsoft.SocialWorker.DTO.WorkModelDto;
import com.geethamsoft.SocialWorker.Exception.SocialWorkerNotFoundException;
import com.geethamsoft.SocialWorker.Model.WorkModel;
import com.geethamsoft.SocialWorker.Repository.WorkRepository;
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
    public WorkModel addDetails(WorkModelDto workModelDto) {
        WorkModel workModel = mapWorkModelDto(workModelDto);

        return workRepository.save(workModel);
    }
private WorkModel mapWorkModelDto(WorkModelDto workModelDto){
        WorkModel workModel = new WorkModel();
        workModel.setName(workModelDto.getName());
        workModel.setLocation(workModelDto.getLocation());
        workModel.setAvailability(workModelDto.getAvailability());
        workModel.setExperience_Level(workModelDto.getExperience_Level());
        workModel.setSpecialisation(workModelDto.getSpecialisation());
        workModel.setWorkType(workModelDto.getWorkType());
        workModel.setChargeAmount(workModelDto.getChargeAmount());
        workModel.setAge(workModelDto.getAge());
        workModel.setGender(workModelDto.getGender());
        workModel.setLanguage_known(workModelDto.getLanguage_known());
        workModel.setDate_Created(workModelDto.getDate_Created());
        return workModel;
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

    public Optional<WorkModel> updateWork(Long id, WorkModelDto workModelDto) {
        Optional<WorkModel> existingWork = workRepository.findById(id);
        if (existingWork.isPresent()) {
            WorkModel updatedService = mapWorkModelDto(workModelDto);

            updatedService.setName(workModelDto.getName());
            updatedService.setLocation(workModelDto.getLocation());
            updatedService.setAvailability(workModelDto.getAvailability());
            updatedService.setExperience_Level(workModelDto.getExperience_Level());
            updatedService.setGender(workModelDto.getLocation());
            updatedService.setLanguage_known(workModelDto.getLanguage_known());
            updatedService.setSpecialisation(workModelDto.getSpecialisation());
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
