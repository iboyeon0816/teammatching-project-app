package com.sphere.demo.repository;

import com.sphere.demo.domain.PortfolioProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<PortfolioProject, Long> {
    List<PortfolioProject> findPortfoiloProjectByUserId(@Param("userId") Long userId);
}
