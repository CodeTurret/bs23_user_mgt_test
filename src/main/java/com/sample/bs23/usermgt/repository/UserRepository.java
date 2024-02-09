package com.sample.bs23.usermgt.repository;

import com.sample.bs23.usermgt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Method to add a new user
      */
    User save(User user);

    /**
     * Method to retrieve user details by ID
      */

    Optional<User> findById(Long id);

    /**
     *  Method to update user details by ID
    */
    User saveAndFlush(User user);

    /**
     *  Method to delete user by ID
    */
    void deleteById(Long id);
}
