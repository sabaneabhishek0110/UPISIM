package com.example.Infrastructure.Persistence;

import com.example.model.User;
import com.example.web.dto.UserDTO;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByPhone(String phone);

    @Query("SELECT u.phone FROM User u")
    List<String> findAllPhoneNumbers();

    @Query("SELECT new com.example.web.dto.UserDTO(u.id,u.phone,u.name) FROM User u WHERE u.id = :id")
    UserDTO getUserById(@Param("id") UUID id);

}
