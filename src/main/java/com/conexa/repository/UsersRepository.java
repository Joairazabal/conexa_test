package com.conexa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.conexa.domain.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    @Query("SELECT U FROM Users U WHERE U.email=:email")
    Optional<Users> findByEmail(@Param("email") String email);

    Boolean existsByEmail(String email);
}
