package com.sphere.demo.domain;

import com.sphere.demo.domain.enums.ProjectState;
import com.sphere.demo.domain.mapping.ProjectMatch;
import com.sphere.demo.domain.mapping.ProjectPlatform;
import com.sphere.demo.domain.mapping.ProjectRecruitPosition;
import com.sphere.demo.domain.mapping.ProjectTechStack;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String body;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer view;

    @Enumerated(EnumType.STRING)
    private ProjectState status;

    private LocalDate deadline;

    private LocalDate CreateDate;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectMatch> projectMatchList = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectPlatform> projectPlatformList = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectRecruitPosition> projectRecruitPositionList = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectTechStack> projectTechStackList = new ArrayList<>();

}
