package com.sphere.demo.repository;

import com.sphere.demo.domain.ResumeSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeSnapshotRepository extends JpaRepository<ResumeSnapshot, Long> {
}
