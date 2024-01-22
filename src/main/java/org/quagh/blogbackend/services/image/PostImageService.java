package org.quagh.blogbackend.services.image;

import lombok.RequiredArgsConstructor;
import org.quagh.blogbackend.entities.Post;
import org.quagh.blogbackend.entities.PostImage;
import org.quagh.blogbackend.exceptions.DataNotFoundException;
import org.quagh.blogbackend.repositories.PostImageRepository;
import org.quagh.blogbackend.repositories.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostImageService implements IImageService<PostImage>{
    private final PostImageRepository postImageRepository;
    private final PostRepository postRepository;

    private final Path root = Paths.get("uploads");

    @Override
    public List<PostImage> getAllImage(){
        return postImageRepository.findAll();
    }

    @Override
    @Transactional
    public List<PostImage> saveImageToDb(Long id, List<String> filenames) throws DataNotFoundException {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Post not found with id: " + id));
        List<PostImage> savedPostImages = new ArrayList<>();
        for (String filename : filenames) {
            PostImage postImage = new PostImage();
            postImage.setPost(post);
            postImage.setUrl(filename);
            savedPostImages.add(postImageRepository.save(postImage));
        }
        return savedPostImages;
    }

    @Override
    @Transactional
    public void deleteImage(Long id){
        postImageRepository.deleteAllByPostId(id);
    }


    @Override
    public String storeFile(MultipartFile file) throws IOException, DataNotFoundException{
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        //Add UUID before filename. Make sure filename is unique
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
        //Path to folder save file
        Path uploadDir = Paths.get("uploads/posts");
        //Check the existing of uploads folder
        if(!Files.exists(uploadDir)){
            Files.createDirectories(uploadDir);
        }
        //Path to file
        Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        //Copy file to folder
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }
}
