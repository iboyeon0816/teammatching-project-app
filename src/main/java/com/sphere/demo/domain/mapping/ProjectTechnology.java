package com.sphere.demo.domain.mapping;
import com.sphere.demo.domain.Project;
import com.sphere.demo.domain.Technology;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectTechnology {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "techStack_id")
    private Technology technology;

    @Builder
    public ProjectTechnology(Technology technology) {
        this.technology = technology;
    }

    public void setProject(Project project) {
        if (this.project != null) {
            throw new IllegalStateException();
        }
        this.project = project;
        project.getProjectTechnologySet().add(this);
    }
}
