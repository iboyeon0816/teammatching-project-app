package com.sphere.demo.repository;

import com.sphere.demo.domain.User;
import com.sphere.demo.domain.mapping.ProjectMatch;
import com.sphere.demo.domain.mapping.ProjectRecruitPosition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectMatchRepository extends JpaRepository<ProjectMatch, Long> {
    boolean existsByUserAndProjectPosition(User user, ProjectRecruitPosition projectPosition);
}
