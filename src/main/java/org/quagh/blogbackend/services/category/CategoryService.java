package org.quagh.blogbackend.services.category;

import org.quagh.blogbackend.entities.Post;
import org.quagh.blogbackend.repositories.PostRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.transaction.annotation.Transactional;
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
    private final PostRepository postRepository;

    @Override
    @Transactional
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
    @Transactional
    public Category updateCategory(Long id, CategoryDTO categoryDTO) throws DataNotFoundException {
        Category existingCategory = getCategoryById(id);
        BeanUtils.copyProperties(categoryDTO, existingCategory, "number_of_posts");
        Long parentId = categoryDTO.getParentId();
        if(parentId!=null){
            Category parentCategory = categoryRepository.findById(parentId)
                    .orElseThrow(() -> new DataNotFoundException("Parent category not found!"));
            existingCategory.setParentCategory(parentCategory);
        }
        return categoryRepository.save(existingCategory);
    }

    @Override
    @Transactional
    public Category deleteCategory(Long id) throws ChangeSetPersister.NotFoundException {
        Category category = categoryRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
        List<Post> associatedPosts = postRepository.findByCategoryId(id);
        List<Category> childCategoryList = categoryRepository.findByParentCategory(category);
        if (!associatedPosts.isEmpty() || !childCategoryList.isEmpty()) {
            throw new IllegalStateException("Cannot delete category with associated post or child category");
        }
        categoryRepository.deleteById(id);
        return category;
    }
}
