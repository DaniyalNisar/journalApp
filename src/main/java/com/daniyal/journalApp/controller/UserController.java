package com.daniyal.journalApp.controller;

import com.daniyal.journalApp.api.WeatherResponse;
import com.daniyal.journalApp.entity.User;
import com.daniyal.journalApp.service.UserService;
import com.daniyal.journalApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Controllers are special types of components that handle http requests
@RestController
@RequestMapping("/user")  //adds mapping to entire class
public class UserController {

    @Autowired
    private UserService userService;

//    @GetMapping
//    public List<User> getAllUsers() {
//        return userService.getAll();
//    }

//    @PostMapping
//    public void createUser(@RequestBody User user) {
//        userService.saveEntry(user);
//    }

    @Autowired
    private WeatherService weatherService;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User oldUser = userService.findByUsername(userName);
        if (oldUser != null) {
            oldUser.setUsername(user.getUsername());
            oldUser.setPassword(user.getPassword());
            userService.saveNewEntry(oldUser);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteByUserName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        userService.deleteUserByUserName(userName);

        return new ResponseEntity<>("Deleted User: " + userName, HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<?> greeting() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();


        StringBuilder message = new StringBuilder();

        WeatherResponse weatherResponse = weatherService.getWeather("Lahore");
        WeatherResponse.Location location = weatherResponse.getLocation();
        WeatherResponse.Current current = weatherResponse.getCurrent();
        WeatherResponse.Condition condition = current.getCondition();

        message.append("Welcome ").append(userName).append("! ");
        message.append("Here's the current weather in ").append(location.getName()).append(": ");
        message.append(condition.getText()).append(", ");
        message.append("Temperature: ").append(current.getTempC()).append("°C (feels like ")
                .append(current.getFeelslikeC()).append("°C). ");
        message.append("Humidity: ").append(current.getHumidity()).append("%, ");
        message.append("Wind: ").append(current.getWindKph()).append(" km/h.");
        return new ResponseEntity<>( message.toString(), HttpStatus.OK);
    }

}
