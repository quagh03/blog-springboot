package org.quagh.blogbackend.services.category;

import lombok.RequiredArgsConstructor;
import org.quagh.blogbackend.dtos.CategoryDTO;
import org.quagh.blogbackend.entities.Category;
import org.quagh.blogbackend.exceptions.DataNotFoundException;
import org.quagh.blogbackend.repositories.CategoryRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    public Category addCategory(CategoryDTO categoryDTO) throws DataNotFoundException {
        Category newCategory = new Category();
        BeanUtils.copyProperties(categoryDTO, newCategory);
        if(categoryDTO.getParentId() != null){
            Category parentCategory = getCategoryById(categoryDTO.getParentId());
            newCategory.setParentCategory(parentCategory);
        }
        newCategory.setNumberOfPosts(0);
        return categoryRepository.save(newCategory);
    }

    @Override
    public Category getCategoryById(Long id) throws DataNotFoundException {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Category not found"));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category updateCategory(Long id, CategoryDTO categoryDTO) throws DataNotFoundException {
        Category existingCategory = getCategoryById(id);
        BeanUtils.copyProperties(categoryDTO, existingCategory, "id", "views");
        return categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
