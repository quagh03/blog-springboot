package org.quagh.blogbackend.services.post;

import lombok.RequiredArgsConstructor;
import org.quagh.blogbackend.dtos.PostDTO;
import org.quagh.blogbackend.entities.Category;
import org.quagh.blogbackend.entities.Post;
import org.quagh.blogbackend.entities.Tag;
import org.quagh.blogbackend.entities.User;
import org.quagh.blogbackend.exceptions.DataNotFoundException;
import org.quagh.blogbackend.exceptions.PostPublishException;
import org.quagh.blogbackend.repositories.CategoryRepository;
import org.quagh.blogbackend.repositories.PostRepository;
import org.quagh.blogbackend.repositories.TagRepository;
import org.quagh.blogbackend.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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
    @Transactional
    public void deletePost(Long id) throws DataNotFoundException{
        Post postToDelete = postRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Post not found with id: " + id));
        postRepository.delete(postToDelete);
    }

    @Override
    @Transactional
    public Post addPost(PostDTO postDTO) throws DataNotFoundException {
        //Create a new post and copy properties from postDTO
        Post newPost = new Post();
        BeanUtils.copyProperties(postDTO, newPost);
        //Find and set author for post
        User author = userRepository.findById(postDTO.getAuthorId())
                .orElseThrow(() -> new DataNotFoundException("Author id not found"));
        newPost.setAuthor(author);
        //Find and set category for post
        Category category = categoryRepository.findById(postDTO.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Category not found"));
        newPost.setCategory(category);
        //Find and set tags for post
        List<Tag> tags = tagRepository.findAllById(postDTO.getTagIds());
        newPost.setTags(new HashSet<>(tags));
        newPost.setViews(0);
        return postRepository.save(newPost);
    }

    @Override
    @Transactional
    public Post publishPost(Long postId) throws DataNotFoundException, PostPublishException {
        //Find post by id
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new DataNotFoundException("Post with id:" + postId + " not found"));
        //Check if post is published
        if(!existingPost.isPublished()){
            //Update published field from false -> true
            existingPost.setPublished(true);
            //Update publishedAt field from null -> currentTime
            existingPost.setPublishedAt(new Date());
            return postRepository.save(existingPost);
        }
        throw new  PostPublishException("Post with id:" + postId + " is already published!");
    }

    @Override
    @Transactional
    public Post unpublishPost(Long postId) throws DataNotFoundException, PostPublishException{
        //Find post by id
        Post existingPost = postRepository.findById(postId)
                .orElseThrow(() -> new DataNotFoundException("Post with id:" + postId + " not found"));
        //Check if post is published
        if(existingPost.isPublished()){
            existingPost.setPublished(false);
            //Update publishedAt field to null
            existingPost.setPublishedAt(null);
            return postRepository.save(existingPost);
        }
        throw new  PostPublishException("Post with id:" + postId + " is already unpublished!");
    }

    @Override
    @Transactional
    public Post updatePost(Long id, PostDTO postDTO) throws  DataNotFoundException{
        //Find existing post by id
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Post not found with id: " + id));

        BeanUtils.copyProperties(postDTO, existingPost);

        User author = userRepository.findById(postDTO.getAuthorId())
                .orElseThrow(() -> new DataNotFoundException("Author id not found"));
        existingPost.setAuthor(author);

        Category category = categoryRepository.findById(postDTO.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException("Category not found"));
        existingPost.setCategory(category);

        // Find and set tags for post
        List<Tag> tags = tagRepository.findAllById(postDTO.getTagIds());
        existingPost.setTags(new HashSet<>(tags));

        return postRepository.save(existingPost);
    }
}
