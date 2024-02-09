package com.sample.bs23.usermgt.service;

import com.sample.bs23.usermgt.model.User;
import com.sample.bs23.usermgt.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl {

    private final UserRepository userRepository;

    /**
     * Add new user.
     * Use CacheEvict to remove entries from the cache.
     * @param user - User type object
     * @return User
     */
    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    public User addUser(User user) {
        // Validate email and password
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }

        // Save the user
        return userRepository.save(user);
    }

    /**
     * Retrieve user details by ID
     * @param id - Long
     * @return User
     */
    @Cacheable(value = "users", key = "#id")
    public User getUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    /**
     * Update user details by ID.
     * Use CachePut to update the cache with the result of the annotated method.
     * @param id - Long
     * @param user - User type object
     * @return User - User object
     */
    @CachePut(value = "users", key = "#user.id")
    @Transactional
    public User updateUserById(Long id, User user) {
        // Retrieve existing user
        User existingUser = getUserById(id);

        // Update user details
        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());

        // Save and return updated user
        return userRepository.saveAndFlush(existingUser);
    }

    /**
     * Delete user by ID
     * @param id - Long id
     */
    public void deleteUserById(Long id) {
        // Check if user exists
        getUserById(id);

        // Delete user
        userRepository.deleteById(id);
    }

    /**
     * Exception class for user not found
     */
    static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

}
