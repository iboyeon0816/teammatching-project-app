package com.sphere.demo.repository;

import com.sphere.demo.domain.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResumeRepository extends JpaRepository<Resume, Long> {
    List<Resume> findPortfoiloProjectByUserId(@Param("userId") Long userId);
}
