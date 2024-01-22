package org.quagh.blogbackend.services.image;

import org.quagh.blogbackend.entities.PostImage;
import org.quagh.blogbackend.exceptions.DataNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IImageService<T> {
    List<T> getAllImage();

    List<T> saveImageToDb(Long id, List<String> filename) throws DataNotFoundException;

    void deleteImage(Long id) throws DataNotFoundException;

    String storeFile(MultipartFile file) throws IOException, DataNotFoundException;
}
