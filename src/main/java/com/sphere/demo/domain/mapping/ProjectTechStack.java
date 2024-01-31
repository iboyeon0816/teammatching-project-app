package com.sphere.demo.domain.mapping;
import com.sphere.demo.domain.Project;
import com.sphere.demo.domain.TechnologyStack;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProjectTechStack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "techStack_id")
    private TechnologyStack technologyStack;


    public void setProject(Project project) {
        if (this.project != null) {
            this.project.getProjectTechStackList().remove(this);
        }
        this.project = project;
        project.getProjectTechStackList().add(this);
    }
}
