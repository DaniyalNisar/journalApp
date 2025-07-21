package com.daniyal.journalApp.service;

import com.daniyal.journalApp.entity.User;
import com.daniyal.journalApp.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserDetailsServiceImplTest {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Mock
    private UserRepository userRepository;

    @Test
    void loadUserByUsernameTest() {
        User mockUser = new User();
        mockUser.setUsername("clark");
        mockUser.setPassword("clark");
        mockUser.setRoles(new ArrayList<String>());

        when(userRepository.findByUsername(ArgumentMatchers.anyString())).thenReturn(mockUser);

        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername("clark");

        Assertions.assertEquals("clark", userDetails.getUsername());
    }
}
