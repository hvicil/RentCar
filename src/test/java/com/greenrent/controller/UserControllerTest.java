package com.greenrent.controller;


import com.greenrent.dto.UserDTO;
import com.greenrent.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    UserService userService;

    @InjectMocks
    UserController underTest;


    @Test
    void getUserByIdTest(){

        UserDTO userDTO=new UserDTO();



    }



}
