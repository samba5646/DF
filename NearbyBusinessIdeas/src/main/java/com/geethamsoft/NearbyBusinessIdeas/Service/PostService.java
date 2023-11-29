package com.geethamsoft.NearbyBusinessIdeas.Service;

import com.geethamsoft.NearbyBusinessIdeas.DTO.PostBusinessDTO;
import com.geethamsoft.NearbyBusinessIdeas.Entity.PostBusinessModel;
import com.geethamsoft.NearbyBusinessIdeas.Exception.PostBusinessNotFoundException;
import com.geethamsoft.NearbyBusinessIdeas.Repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PostService {
    private PostRepository postRepository;

    public PostBusinessModel addDetails(PostBusinessDTO postBusinessDTO) {
        PostBusinessModel postBusinessModel = mapPostBusinessDTO(postBusinessDTO);

        return postRepository.save(postBusinessModel);
    }

    private PostBusinessModel mapPostBusinessDTO(PostBusinessDTO postBusinessDTO) {
        PostBusinessModel postBusinessModel = new PostBusinessModel();
        postBusinessModel.setTitle(postBusinessDTO.getTitle());
        postBusinessModel.setCategory(postBusinessDTO.getCategory());
        postBusinessModel.setFormat(postBusinessDTO.getFormat());
        postBusinessModel.setContent(postBusinessDTO.getContent());
        postBusinessModel.setPrice(postBusinessDTO.getPrice());
        postBusinessModel.setContactInformation(postBusinessDTO.getContactInformation());
        return postBusinessModel;
    }


    public List<PostBusinessModel> getAllBusinessDetails() {

        return postRepository.findAll();
    }

    public Optional<PostBusinessModel> getBusinessById(Long id) {
        if (postRepository.findById(id).isEmpty()) {
            throw new PostBusinessNotFoundException("Requested particular id was not found");
        }
        return postRepository.findById(id);
    }


    public Optional<PostBusinessModel> updateBusinessWork(Long id,PostBusinessDTO postBusinessDTO) {
        Optional<PostBusinessModel> existingBusiness = postRepository.findById(id);
        if (existingBusiness.isPresent()) {
            PostBusinessModel updatedService = mapPostBusinessDTO(postBusinessDTO);

            updatedService.setTitle(postBusinessDTO.getTitle());
            updatedService.setCategory(postBusinessDTO.getCategory());
            updatedService.setFormat(postBusinessDTO.getFormat());
            updatedService.setContent(postBusinessDTO.getContent());
            updatedService.setPrice(postBusinessDTO.getPrice());
            updatedService.setContactInformation(postBusinessDTO.getContactInformation());
            return Optional.of(postRepository.save(updatedService));
        }
        throw new PostBusinessNotFoundException("id not found");

    }


    public void deleteBusiness(Long id) {
        Optional<PostBusinessModel> existingBusiness = postRepository.findById(id);
        if (existingBusiness.isPresent()) {
            postRepository.deleteById(id);
        } else {
            throw new PostBusinessNotFoundException("details are not available in Database");
        }
    }
}
