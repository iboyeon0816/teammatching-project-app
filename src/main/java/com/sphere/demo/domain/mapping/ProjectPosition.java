package com.sphere.demo.domain.mapping;

import com.sphere.demo.domain.Position;
import com.sphere.demo.domain.Project;
import com.sphere.demo.domain.enums.ApplicationState;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.sphere.demo.domain.enums.ApplicationState.*;

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
    private List<ProjectApplication> projectApplicationList;

    @Builder
    public ProjectPosition(Integer memberCount, Position position) {
        this.memberCount = memberCount;
        this.position = position;
        this.projectApplicationList = new ArrayList<>();
    }

    public void setProject(Project project) {
        if (this.project != null) {
            throw new IllegalStateException();
        }
        this.project = project;
        project.getProjectPositionSet().add(this);
    }

    public boolean isPositionFull() {
        Integer approvedCount = 0;
        List<ProjectApplication> applicationList = this.getProjectApplicationList();
        for (ProjectApplication projectApplication : applicationList) {
            if (projectApplication.getState().equals(APPROVED)) {
                approvedCount++;
            }
        }
        return this.memberCount <= approvedCount;
    }

    public Integer getMatchedCount() {
        return (int) projectApplicationList.stream()
                .filter(application -> application.getState().equals(APPROVED))
                .count();
    }
}
