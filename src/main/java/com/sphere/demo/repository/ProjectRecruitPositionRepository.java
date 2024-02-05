package com.sphere.demo.repository;

import com.sphere.demo.domain.Position;
import com.sphere.demo.domain.Project;
import com.sphere.demo.domain.mapping.ProjectRecruitPosition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRecruitPositionRepository extends JpaRepository<ProjectRecruitPosition, Long> {

    Optional<ProjectRecruitPosition> findByProjectAndPosition(Project project, Position position);
}
