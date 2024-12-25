package com.sphere.demo.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResumeTechnology {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;

    @Builder
    public ResumeTechnology(String name) {
        this.name = name;
    }

    public void setResume(Resume resume) {
        if (this.resume != null) {
            throw new IllegalStateException();
        }
        this.resume = resume;
        resume.getResumeTechnologySet().add(this);
    }
}
