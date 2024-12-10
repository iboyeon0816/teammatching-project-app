package com.sphere.demo.repository;

import com.sphere.demo.domain.Technology;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechnologyRepository extends JpaRepository<Technology, Long> {
}
