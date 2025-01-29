package com.sphere.demo.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ResumeProject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private String techStacks;

    @Column(nullable = false)
    private String serviceContents;

    @Column(nullable = false)
    private String devDetails;

    @Column(nullable = false)
    private String growthDetails;

    private String relativeActivities;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;

    public void setResume(Resume resume) {
        if (this.resume != null) {
            throw new IllegalStateException();
        }
        this.resume = resume;
        resume.getResumeProjectList().add(this);
    }
}
