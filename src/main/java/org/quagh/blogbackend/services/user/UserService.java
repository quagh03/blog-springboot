package org.quagh.blogbackend.services.user;

import lombok.RequiredArgsConstructor;
import org.quagh.blogbackend.components.JwtTokenUtil;
import org.quagh.blogbackend.dtos.UserDTO;
import org.quagh.blogbackend.entities.Role;
import org.quagh.blogbackend.entities.User;
import org.quagh.blogbackend.exceptions.DataNotFoundException;
import org.quagh.blogbackend.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public User addUser(UserDTO userDTO){
        String phoneNumber = userDTO.getPhoneNumber();
        String username = userDTO.getUsername();
        String email = userDTO.getEmail();
        User newUser = new User();

        if (userRepository.existsByEmail(email) || userRepository.existsByPhoneNumber(phoneNumber) || userRepository.existsByUsername(username)) {
            throw new DataIntegrityViolationException("User with given email, phone number, or username already exists!");
        }

        if(userDTO.getFacebookAccountId() == 0 && userDTO.getGoogleAccountId() == 0){
            String password = userDTO.getPasswordHash();
            String encodedPassword = passwordEncoder.encode(password);
            newUser.setPasswordHash(encodedPassword);
        }

        BeanUtils.copyProperties(userDTO, newUser);
        String randomCode = String.valueOf(UUID.randomUUID());
        newUser.setVerifitaionCode(randomCode);
        newUser.setActive(false);
        newUser.setRole(Role.ROLE_GUEST);
        return userRepository.save(newUser);
    }

    @Override
    public boolean verify(String verificationCode){
        User user = userRepository.findByVerifitaionCode(verificationCode);
        if(user == null || user.isActive()){
            return false;
        }else{
            user.setVerifitaionCode(null);
            user.setActive(true);
            userRepository.save(user);
            return true;
        }
    }

    @Override
    public String login(String username, String password) throws DataNotFoundException {
        User loginUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new DataNotFoundException("Invalid username or password"));

        //Credential check
        if(loginUser.getFacebookAccountId() == 0 && loginUser.getGoogleAccountId() == 0){
            if(!passwordEncoder.matches(password, loginUser.getPassword())){
                throw new BadCredentialsException("Wrong username or password!");
            }
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                username, password
        );

        //Authenticate with Spring Security
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(loginUser);
    }
}
