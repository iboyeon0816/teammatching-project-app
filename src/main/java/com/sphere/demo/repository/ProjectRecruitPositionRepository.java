package com.sphere.demo.repository;

import com.sphere.demo.domain.Position;
import com.sphere.demo.domain.Project;
import com.sphere.demo.domain.mapping.ProjectPosition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRecruitPositionRepository extends JpaRepository<ProjectPosition, Long> {

    Optional<ProjectPosition> findByProjectAndPosition(Project project, Position position);
}
