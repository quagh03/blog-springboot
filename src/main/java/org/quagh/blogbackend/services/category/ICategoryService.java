package org.quagh.blogbackend.services.category;

import org.quagh.blogbackend.dtos.CategoryDTO;
import org.quagh.blogbackend.entities.Category;
import org.quagh.blogbackend.exceptions.DataNotFoundException;

import java.util.List;

public interface ICategoryService {
    Category addCategory(CategoryDTO categoryDTO) throws DataNotFoundException;
    Category getCategoryById(Long id) throws DataNotFoundException;
    List<Category> getAllCategories();
    Category updateCategory(Long id, CategoryDTO categoryDTO) throws DataNotFoundException;
    void deleteCategory(Long id);
}
