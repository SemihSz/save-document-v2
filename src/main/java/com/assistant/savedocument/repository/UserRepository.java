package com.assistant.savedocument.repository;

import com.assistant.savedocument.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Semih, 2.07.2023
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    Optional<UserEntity> findByUsername(String username);

    @Query(value = "SELECT * FROM USER_ENTITY t WHERE t.USERNAME=:username", nativeQuery = true)
    UserEntity findByUsernameEntity(@Param("username") String username);
}