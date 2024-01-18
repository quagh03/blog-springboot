package org.quagh.blogbackend.services.image;

import org.quagh.blogbackend.entities.PostImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IImageService<T> {
    List<T> getAllImage();
    String storeFile(MultipartFile file) throws IOException;
}
