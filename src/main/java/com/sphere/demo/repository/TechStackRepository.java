package com.sphere.demo.repository;

import com.sphere.demo.domain.TechnologyStack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TechStackRepository extends JpaRepository<TechnologyStack, Long> {
}
