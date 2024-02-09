package com.sample.bs23.usermgt.controller;

import com.sample.bs23.usermgt.kafka.producer.KafkaJsonProducer;
import com.sample.bs23.usermgt.model.User;
import com.sample.bs23.usermgt.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userService;

    private final KafkaJsonProducer kafkaProducer;


    /**
     * Add new user
     * @param user - user object
     * @return ResponseEntity - Object
     */
    @PostMapping("/add-user")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User newUser = userService.addUser(user);
        // send to kafka topic
        log.info("Send to Kafka Producer");
        kafkaProducer.sendMessage(newUser);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    /**
     * Retrieve user details by ID
     * @param id - Long
     * @return ResponseEntity - Object
     */
    @GetMapping("/get-user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    /**
     * Update user details by ID
     * @param id - long
     * @param user - Object
     * @return ResponseEntity - Object
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<User> updateUserById(@PathVariable Long id, @RequestBody User user) {
        try {
            User userRetrieved = userService.getUserById(id);
            if(ObjectUtils.isEmpty(userRetrieved)){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            User updatedUser = userService.updateUserById(id, user);
            // send to kafka topic
            log.info("Send to Kafka Producer");
            kafkaProducer.sendMessage(updatedUser);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Delete user by ID
     * @param id - Long
     * @return ResponseEntity - String
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id) {
        try {
            User userRetrieved = userService.getUserById(id);
            if (ObjectUtils.isEmpty(userRetrieved)) {
                return new ResponseEntity<>("No User Found", HttpStatus.NOT_FOUND);
            }
            userService.deleteUserById(id);
            // send to kafka topic
            log.info("Send to Kafka Producer");
            kafkaProducer.sendMessage(userRetrieved);
            return new ResponseEntity<>("User deleted", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Not Found", HttpStatus.NOT_FOUND);
        }
    }
}
