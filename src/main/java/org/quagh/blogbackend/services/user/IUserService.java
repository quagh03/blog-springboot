package org.quagh.blogbackend.services.user;

import org.quagh.blogbackend.dtos.UserDTO;
import org.quagh.blogbackend.entities.User;

public interface IUserService {
    User addUser(UserDTO userDTO);

    boolean verify(String verificationCode);

    String login(String username, String password);
}
