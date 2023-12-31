package org.quagh.blogbackend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.quagh.blogbackend.dtos.TagDTO;
import org.quagh.blogbackend.entities.Tag;
import org.quagh.blogbackend.responses.TagResponse;
import org.quagh.blogbackend.services.tag.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/tags")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @PostMapping("")
    public ResponseEntity<?> addTag(
            @Valid @RequestBody TagDTO tagDTO,
            BindingResult result){
        TagResponse tagResponse = new TagResponse();
        if(result.hasErrors()){
            List<String> errorMessages = result.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList();
            tagResponse.setMessage("INSERT_TAG_FAILED");
            tagResponse.setErrors(errorMessages);
            return ResponseEntity.badRequest().body(tagResponse);
        }
        try {
            Tag addedTag = tagService.addTag(tagDTO);
            tagResponse.setMessage("INSERT_TAG_SUCCESSFULLY");
            tagResponse.setTag(addedTag);
            return ResponseEntity.ok(tagResponse);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getAllTags(@RequestParam(defaultValue = "1") int page,
                                        @RequestParam(defaultValue = "10") int limit){
        List<Tag> tagList = tagService.getAllTags();
        return ResponseEntity.ok(tagList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTagById(@PathVariable Long id){
        TagResponse tagResponse = new TagResponse();
        try {
            Tag selectedTag = tagService.getTagById(id);
            tagResponse.setMessage("GET_TAG_SUCCESFULLY");
            tagResponse.setTag(selectedTag);
            return ResponseEntity.ok(tagResponse);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable Long id){
        TagResponse tagResponse = new TagResponse();
        try {
            Tag deletedTag = tagService.deleteTag(id);
            tagResponse.setMessage("DELETE_TAG_SUCCESFULLY");
            tagResponse.setTag(deletedTag);
            return ResponseEntity.ok(tagResponse);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTag(
            @PathVariable Long id,
            @RequestBody TagDTO tagDTO,
            BindingResult result){
        TagResponse tagResponse = new TagResponse();
        if(result.hasErrors()) {
            List<String> errorMessages = result.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList();
            tagResponse.setMessage("UPDATE_CATEGORY_FAILED");
            tagResponse.setErrors(errorMessages);
            return ResponseEntity.badRequest().body(tagResponse);
        }
        try {
            Tag updatedTag = tagService.updateTag(id, tagDTO);
            tagResponse.setMessage("UPDATE_TAG_SUCCESSFULLY");
            tagResponse.setTag(updatedTag);
            return ResponseEntity.ok(tagResponse);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
