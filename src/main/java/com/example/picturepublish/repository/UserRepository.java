package com.example.picturepublish.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.picturepublish.entity.UserInfo;

public interface UserRepository  extends JpaRepository<UserInfo, Long> {
    UserInfo findByUsername(String username);
    Optional<UserInfo> findById(Long id);

}
