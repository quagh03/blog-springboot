package org.quagh.blogbackend.controllers;

import lombok.RequiredArgsConstructor;
import org.quagh.blogbackend.services.image.IImageService;
import org.quagh.blogbackend.services.image.PostImageService;
import org.quagh.blogbackend.services.image.UserImageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/images")
@RequiredArgsConstructor
public class ImageController {
    private final UserImageService userImageService;
    private final PostImageService postImageService;
    @PostMapping("/user")
    public ResponseEntity<?> uploadUserProfileImage(@RequestParam("image")MultipartFile file){
        return uploadImage(file, userImageService);
    }

    @PostMapping("/post")
    public ResponseEntity<?> uploadPostImage(@RequestParam("image")MultipartFile file){
        return uploadImage(file, postImageService);
    }

    //Image Upload Method
    private ResponseEntity<?> uploadImage(MultipartFile file, IImageService imageService) {
        try {
            // Check if request doesn't have file
            if (file == null) {
                return ResponseEntity.badRequest().body("File must not be empty");
            }

            // Check file size and file type
            if (file.getSize() > 10 * 1024 * 1024) {
                return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                        .body("File is too large! Maximum size is 10MB");
            }

            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                        .body("File must be an image!");
            }

            String filename = imageService.storeFile(file);
            return ResponseEntity.ok(filename);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
