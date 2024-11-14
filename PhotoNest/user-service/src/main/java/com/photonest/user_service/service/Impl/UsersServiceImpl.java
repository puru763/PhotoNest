package com.photonest.user_service.service.Impl;

import com.photonest.user_service.data.UserEntity;
import com.photonest.user_service.data.UserRepository;
import com.photonest.user_service.service.UsersService;
import com.photonest.user_service.shared.UserDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsersServiceImpl  implements UsersService {

     @Autowired
     UserRepository userRepository;

    public UsersServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        userDTO.setUserId(UUID.randomUUID().toString());
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
        userEntity.setEncryptedPassword("working");
        userRepository.save(userEntity);
        UserDTO user = modelMapper.map(userEntity, UserDTO.class);
        return user;
    }
}
