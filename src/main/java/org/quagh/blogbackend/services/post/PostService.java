package org.quagh.blogbackend.services.post;

import lombok.RequiredArgsConstructor;
import org.quagh.blogbackend.dtos.PostDTO;
import org.quagh.blogbackend.entities.Category;
import org.quagh.blogbackend.entities.Post;
import org.quagh.blogbackend.entities.Tag;
import org.quagh.blogbackend.entities.User;
import org.quagh.blogbackend.exceptions.DataNotFoundException;
import org.quagh.blogbackend.repositories.CategoryRepository;
import org.quagh.blogbackend.repositories.PostRepository;
import org.quagh.blogbackend.repositories.TagRepository;
import org.quagh.blogbackend.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService implements IPostService{
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Override
    public Page<Post> getAllPosts(PageRequest pageRequest){
        return null;
    }

    @Override
    public Post getPostById(Long id) throws DataNotFoundException {
        return postRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Post with id not found"));
    }

    @Override
    public void deletePost(Long id) throws DataNotFoundException{

        Post postToDelete = postRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Post not found with id: " + id));

        postRepository.delete(postToDelete);

    }

    @Override
    @Transactional
    public Post addPost(PostDTO postDTO) throws DataNotFoundException {
        Post newPost = new Post();
        BeanUtils.copyProperties(postDTO, newPost);

        User author = userRepository.findById(postDTO.getAuthorId())
                .orElseThrow(() -> new DataNotFoundException("Author id not found"));
        newPost.setAuthor(author);

        Category category = categoryRepository.findById(postDTO.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Category not found"));

        newPost.setCategory(category);

        List<Tag> tags = tagRepository.findAllById(postDTO.getTagIds());
        newPost.setTags(new HashSet<>(tags));

        return postRepository.save(newPost);
    }
}
