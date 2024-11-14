package com.photonest.user_service.service;

import com.photonest.user_service.shared.UserDTO;

public interface UsersService   {
    UserDTO createUser(UserDTO userDTO);
}
