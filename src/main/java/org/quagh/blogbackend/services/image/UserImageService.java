package org.quagh.blogbackend.services.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserImageService implements IImageService<String>{

    @Override
    public List<String> getAllImage() {
        return null;
    }


    @Override
    public String storeFile(MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        //Add UUID before filename. Make sure filename is unique
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;
        //Path to folder save file
        Path uploadDir = Paths.get("uploads/users");
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
