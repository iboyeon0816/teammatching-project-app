package com.sphere.demo.repository;

import com.sphere.demo.domain.User;
import com.sphere.demo.domain.mapping.ProjectApplication;
import com.sphere.demo.domain.mapping.ProjectPosition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectApplicationRepository extends JpaRepository<ProjectApplication, Long> {
    boolean existsByUserAndProjectPosition(User user, ProjectPosition projectPosition);
}
