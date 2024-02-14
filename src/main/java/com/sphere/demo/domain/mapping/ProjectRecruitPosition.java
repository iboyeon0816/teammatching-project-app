package com.sphere.demo.domain.mapping;
import com.sphere.demo.domain.Position;
import com.sphere.demo.domain.Project;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ProjectRecruitPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer memberCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id")
    private Position position;

    @OneToMany(mappedBy = "projectPosition", cascade = CascadeType.ALL)

    private List<ProjectMatch> projectMatchList;

    public void setProject(Project project) {
        if (this.project != null) {
            this.project.getProjectRecruitPositionSet().remove(this);
        }
        this.project = project;
        project.getProjectRecruitPositionSet().add(this);

    }
}
