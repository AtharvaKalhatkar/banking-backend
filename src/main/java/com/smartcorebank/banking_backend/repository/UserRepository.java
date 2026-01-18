package com.smartcorebank.banking_backend.repository;

import com.smartcorebank.banking_backend.entity.User; // IMPORT FROM ENTITY
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}