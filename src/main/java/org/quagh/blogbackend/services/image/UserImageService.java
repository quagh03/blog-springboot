package org.quagh.blogbackend.services.image;

import lombok.RequiredArgsConstructor;
import org.quagh.blogbackend.entities.User;
import org.quagh.blogbackend.exceptions.DataNotFoundException;
import org.quagh.blogbackend.repositories.UserRepository;
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
    private final UserRepository userRepository;

    @Override
    public List<String> getAllImage() {
        return null;
    }

    @Override
    public String getImageById(Long id) throws DataNotFoundException {
        return null;
    }

    @Override
    public List<String> saveImageToDb(Long id, List<String> filename) throws DataNotFoundException {
        return null;
    }

    @Override
    public void deleteImage(Long id) throws DataNotFoundException, IOException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("User not found with id: " + id));
        String filename = user.getProfile();
        if(!filename.isEmpty()){
            deleteFile(filename);
            filename = "";
        }
        user.setProfile(filename);
        userRepository.save(user);
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

    @Override
    public void deleteFile(String filename) throws IOException {
        Path filePath = Paths.get("uploads/users", filename);
        try {
            // Delete the file
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            // Handle any exceptions
            e.printStackTrace();
        }
    }


}
