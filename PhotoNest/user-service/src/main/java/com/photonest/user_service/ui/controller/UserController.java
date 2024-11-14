package com.photonest.user_service.ui.controller;

import com.photonest.user_service.service.UsersService;
import com.photonest.user_service.shared.UserDTO;
import com.photonest.user_service.ui.model.UserRequestModel;
import com.photonest.user_service.ui.model.UserResponseModel;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    final UsersService usersService;

    public UserController(UsersService usersService) {
        this.usersService = usersService;
    }

//
//    //  below  code   will  tell  you on  which  instace   ur  request  is   going   (with  the  nhelp  of  an  enviroment )
//    @Autowired
//    private Environment env;
//
//    @GetMapping()
//    public String name(){
//        return "working on  port  "+  env.getProperty("local.server.port");
//    }

    @PostMapping
    ResponseEntity createUser( @Valid @RequestBody UserRequestModel userRequestModel){

        //here  model mapper   si  ued  to  chek  that  while mapping   the   thing  shoudbe  same   i men  what  ever data  si  there    in reqest     it  and  repsone  it    shoud be  there
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        //  this     will  sued  to  tlee    to  conveert   to    what  to  what
        UserDTO userDto = modelMapper.map(userRequestModel, UserDTO.class);
        UserDTO createdUser = usersService.createUser(userDto);
//        UserResponseModel returnValue = modelMapper.map(createdUser, UserResponseModel.class);
        return (ResponseEntity) ResponseEntity.status(HttpStatus.CREATED);
    }
}
