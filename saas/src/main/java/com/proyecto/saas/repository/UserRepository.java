package com.proyecto.saas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyecto.saas.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); 
    Optional<User> findByFirstName(String firstName);
    
    @Modifying
    @Query("update User u set u.firstName=:firstName, u.lastName=:lastName, u.email=:email where u.id = :id")
    void updateUser(@Param(value = "id") Long id, @Param(value = "firstName") String firstName, @Param(value = "lastName") String lastName, @Param(value = "email") String email);
}
