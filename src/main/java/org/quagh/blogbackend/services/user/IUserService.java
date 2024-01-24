package org.quagh.blogbackend.services.user;

import org.quagh.blogbackend.dtos.UserDTO;
import org.quagh.blogbackend.entities.User;
import org.quagh.blogbackend.exceptions.DataNotFoundException;

public interface IUserService {
    User addUser(UserDTO userDTO);

    boolean verify(String verificationCode);

    String login(String username, String password) throws DataNotFoundException;
}
