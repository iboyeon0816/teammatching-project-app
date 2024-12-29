package com.sphere.demo.repository;

import com.sphere.demo.domain.Project;
import com.sphere.demo.domain.User;
import com.sphere.demo.domain.mapping.ProjectFavorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectFavoriteRepository extends JpaRepository<ProjectFavorite, Long> {

    Optional<ProjectFavorite> findByUserAndProject(User user, Project project);
}
