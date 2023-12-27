package org.quagh.blogbackend.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {
    @PostMapping("")
    public ResponseEntity<?> uploadImage(@RequestParam("image")MultipartFile file){
        try {
            //Check if request doesn't have file
            if(file != null){
                //Check file size and file type
                if(file.getSize() > 10 * 1024 * 1024){
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                            .body("File is to large! Maximum size is 10MB");
                }
                String contentType = file.getContentType();
                if(contentType == null || !contentType.startsWith("image/")){
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                            .body("File must be an image!");
                }
                String filename = storeFile(file);
                return ResponseEntity.ok(filename);
            }
            return ResponseEntity.badRequest().body("File must not be empty");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String storeFile(MultipartFile file) throws IOException{
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        //Add UUID before filename. Make sure filename is unique
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
        //Path to folder save file
        Path uploadDir = Paths.get("uploads");
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
