package org.quagh.blogbackend.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.quagh.blogbackend.dtos.CategoryDTO;
import org.quagh.blogbackend.entities.Category;
import org.quagh.blogbackend.responses.CategoryResponse;
import org.quagh.blogbackend.services.category.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
//Dependency Injection
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("")
    public ResponseEntity<?> addCategory(
            @Valid @RequestBody CategoryDTO categoryDTO,
            BindingResult result){
        CategoryResponse categoryResponse = new CategoryResponse();
        if(result.hasErrors()){
            List<String> errorMessages = result.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList();
            categoryResponse.setMessage("INSERT_CATEGORY_FAILED");
            categoryResponse.setErrors(errorMessages);
            return ResponseEntity.badRequest().body(categoryResponse);
        }
        try {
            Category addedCategory = categoryService.addCategory(categoryDTO);
            categoryResponse.setMessage("INSERT_CATEGORY_SUCCESSFULLY");
            categoryResponse.setCategory(addedCategory);
            return ResponseEntity.ok(categoryResponse);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id){
        CategoryResponse categoryResponse = new CategoryResponse();
        try {
            Category selectedCategory = categoryService.getCategoryById(id);
            categoryResponse.setMessage("Get category successfully!");
            categoryResponse.setCategory(selectedCategory);
            return ResponseEntity.ok(categoryResponse);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getAllCategories(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int limit){
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(
            @PathVariable Long id,
            @RequestBody CategoryDTO categoryDTO,
            BindingResult result){

        CategoryResponse categoryResponse = new CategoryResponse();
        if(result.hasErrors()){
            List<String> errorMessages = result.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList();
            categoryResponse.setMessage("UPDATE_CATEGORY_FAILED");
            categoryResponse.setErrors(errorMessages);
            return ResponseEntity.badRequest().body(categoryResponse);
        }
        try {
            Category updatedCategory = categoryService.updateCategory(id, categoryDTO);
            categoryResponse.setMessage("UPDATE_CATEGORY_SUCCESSFULLY");
            categoryResponse.setCategory(updatedCategory);
            return ResponseEntity.ok(categoryResponse);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id){
        CategoryResponse categoryResponse = new CategoryResponse();
        try {
            Category deletedCategory = categoryService.deleteCategory(id);
            categoryResponse.setMessage("DELETE_CATEGORY_SUCCESSFULLY");
            categoryResponse.setCategory(deletedCategory);
            return ResponseEntity.ok(categoryResponse);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
