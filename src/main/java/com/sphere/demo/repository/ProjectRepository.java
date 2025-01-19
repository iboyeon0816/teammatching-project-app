package com.sphere.demo.repository;

import com.sphere.demo.domain.Project;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("SELECT p FROM Project p WHERE p.deadline < :today AND p.status = 'RECRUITING'")
    List<Project> findExpiredProjects(@Param("today") LocalDate now);

    @Query("SELECT p FROM Project p ORDER BY p.status, p.view DESC, p.createdAt DESC")
    List<Project> findMainProjects(Pageable pageable);
}
