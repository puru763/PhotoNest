package com.photonest.user_service.service;

import com.photonest.user_service.shared.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsersService extends UserDetailsService {
    UserDTO createUser(UserDTO userDTO);
}
