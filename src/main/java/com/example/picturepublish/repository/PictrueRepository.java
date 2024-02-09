package com.example.picturepublish.repository;

import com.example.picturepublish.entity.Pictrue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PictrueRepository extends JpaRepository<Pictrue, Long> {
    List<Pictrue> findByStatus(String status);
}
