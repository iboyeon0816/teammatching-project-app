package com.sphere.demo.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResumeSnapshotTechnology {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_snapshot_id", nullable = false)
    private ResumeSnapshot resumeSnapshot;

    @Builder
    public ResumeSnapshotTechnology(String name, ResumeSnapshot resumeSnapshot) {
        this.name = name;
    }

    public void setResumeSnapshot(ResumeSnapshot resumeSnapshot) {
        if (this.resumeSnapshot != null) {
            throw new IllegalStateException();
        }

        this.resumeSnapshot = resumeSnapshot;
        resumeSnapshot.getResumeSnapshotTechnologySet().add(this);
    }
}
