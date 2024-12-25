package com.sphere.demo.domain.mapping;
import com.sphere.demo.domain.Position;
import com.sphere.demo.domain.Project;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectPosition {
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

    @Builder
    public ProjectPosition(Integer memberCount, Position position) {
        this.memberCount = memberCount;
        this.position = position;
        this.projectMatchList = new ArrayList<>();
    }

    public void setProject(Project project) {
        if (this.project != null) {
            throw new IllegalStateException();
        }
        this.project = project;
        project.getProjectPositionSet().add(this);
    }
}
