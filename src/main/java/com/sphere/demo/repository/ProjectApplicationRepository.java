package com.sphere.demo.repository;

import com.sphere.demo.domain.mapping.ProjectApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectApplicationRepository extends JpaRepository<ProjectApplication, Long> {
}
