package com.filemanager.repository;

import com.filemanager.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByEmail(String email);

    UserEntity findByUsername(String name);

    boolean existsByEmailIgnoreCase(String email);
}
