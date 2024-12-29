package com.sphere.demo.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectTechnology {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @Builder
    public ProjectTechnology(String name) {
        this.name = name;
    }

    public void setProject(Project project) {
        if (this.project != null) {
            throw new IllegalStateException();
        }
        this.project = project;
        project.getProjectTechnologySet().add(this);
    }
}
