package com.daniyal.journalApp.service;

import com.daniyal.journalApp.entity.User;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @ParameterizedTest
    @ValueSource(strings = {"admin", "bruce", "clark"})
    public void testFindByUsername(String username) {
        User user = userService.findByUsername(username);
        assertEquals(username, user.getUsername());
    }

    //@Disabled
    @ParameterizedTest
    @CsvSource({"1,1,2", "2,10,12", "3,3,9"})
    public void test(int a, int b, int expected) {
        assertEquals(expected, a + b, "The result should be " + expected);
        //assertTrue();
        //assertNotNull();

    }


//@ArgumentsSource() for custom arguments formulation/implementation

}
