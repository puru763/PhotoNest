package com.photonest.user_service.service.Impl;

import com.photonest.user_service.data.UserEntity;
import com.photonest.user_service.data.UserRepository;
import com.photonest.user_service.service.UsersService;
import com.photonest.user_service.shared.UserDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class UsersServiceImpl  implements UsersService {

     @Autowired
     UserRepository userRepository;
     @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;


    public UsersServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        userDTO.setUserId(UUID.randomUUID().toString());
        userDTO.setEncryptedPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = modelMapper.map(userDTO, UserEntity.class);
        userRepository.save(userEntity);
        UserDTO user = modelMapper.map(userEntity, UserDTO.class);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       UserEntity userEntity =   userRepository.findByEmail(username);
       if(userEntity ==  null)  throw new UsernameNotFoundException(username);
        return new User(userEntity.getEmail() , userEntity.getEncryptedPassword() , true , true , true , true, new ArrayList<>());
    }
}
