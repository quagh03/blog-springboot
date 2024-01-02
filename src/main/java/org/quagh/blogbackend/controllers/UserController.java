package org.quagh.blogbackend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.quagh.blogbackend.dtos.UserDTO;
import org.quagh.blogbackend.dtos.UserLoginDTO;
import org.quagh.blogbackend.entities.User;
import org.quagh.blogbackend.services.mailsender.MailSenderService;
import org.quagh.blogbackend.services.user.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final MailSenderService mailSenderService;
    @PostMapping("/register")
    public ResponseEntity<?> addUser(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult result,
            HttpServletRequest request){
        try {
            if(result.hasErrors()){
                List<String> errorMessages = result.getAllErrors()
                        .stream()
                        .map(ObjectError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            if(!userDTO.getPasswordHash().equals(userDTO.getRetypePassword())){
                return ResponseEntity.badRequest().body("Password does not match!");
            }
            User addedUser = userService.addUser(userDTO);
//            mailSenderService.sendVerificationEmail(addedUser, getSiteUrl(request));
            return ResponseEntity.ok("Register Successfully" + addedUser);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<?> verifyUser(@Param("code") String code){
        if(userService.verify(code)){
            return ResponseEntity.ok("Verified!");
        }else{
            return ResponseEntity.badRequest().body("Failed");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO userLoginDTO){
        return ResponseEntity.ok("some body");
    }

    private String getSiteUrl(HttpServletRequest request){
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "/api/v1/users");
    }
}
