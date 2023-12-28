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
            categoryResponse.setMessage("Create Category Failed");
            categoryResponse.setErrors(errorMessages);
            return ResponseEntity.badRequest().body(categoryResponse);
        }
        try {
            Category addedCategory = categoryService.addCategory(categoryDTO);
            categoryResponse.setMessage("Created Category");
            categoryResponse.setCategory(addedCategory);
            return ResponseEntity.ok(categoryResponse);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<?> getCategoryById(Long id){
//
//    }

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
            @RequestBody CategoryDTO categoryDTO){
        try {
            categoryService.updateCategory(id, categoryDTO);
            return ResponseEntity.ok("This is updateCategory");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id){
        return ResponseEntity.ok("This is deleteCategory");
    }

}
