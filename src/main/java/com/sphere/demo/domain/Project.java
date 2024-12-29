package com.sphere.demo.domain;

import com.sphere.demo.domain.common.BaseEntity;
import com.sphere.demo.domain.enums.ProjectState;
import com.sphere.demo.domain.mapping.ProjectPlatform;
import com.sphere.demo.domain.mapping.ProjectPosition;
import com.sphere.demo.web.dto.project.ProjectRequestDto.ProjectDetailDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String body;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    private LocalDate deadline;

    @Enumerated(EnumType.STRING)
    private ProjectState status;

    private String imagePath;

    private Integer view;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // 작성자

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectPlatform> projectPlatformSet;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectPosition> projectPositionSet;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectTechnology> projectTechnologySet;

    @Builder
    public Project(String title, String body, LocalDate startDate, LocalDate endDate, LocalDate deadline) {
        this.title = title;
        this.body = body;
        this.startDate = startDate;
        this.endDate = endDate;
        this.deadline = deadline;
        this.view = 0;
        this.status = ProjectState.RECRUITING;
        this.projectPlatformSet = new HashSet<>();
        this.projectPositionSet = new HashSet<>();
        this.projectTechnologySet = new HashSet<>();
    }

    public void setUser(User user) {
        if (this.user != null) {
            throw new IllegalStateException();
        }

        this.user = user;
        user.getProjectList().add(this);
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void viewUp() {
        this.view++;
    }

    public void update(ProjectDetailDto updateDto) {
        this.title = updateDto.getTitle();
        this.body = updateDto.getBody();
        this.startDate = updateDto.getStartDate();
        this.endDate = updateDto.getEndDate();
        this.deadline = updateDto.getDeadline();
    }

    public void clearAssociations() {
        this.getProjectTechnologySet().clear();
        this.getProjectPositionSet().clear();
        this.getProjectPlatformSet().clear();
    }

    public void setClose() {
        this.status = ProjectState.COMPLETED;
    }
}
