package com.sphere.demo.domain;

import com.sphere.demo.domain.mapping.ProjectRecruitPosition;
import com.sphere.demo.domain.mapping.UserPosition;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "position", cascade = CascadeType.ALL)
    private List<UserPosition> userPositionList = new ArrayList<>();

    @OneToMany(mappedBy = "position", cascade = CascadeType.ALL)
    private List<ProjectRecruitPosition> projectRecruitPositionList = new ArrayList<>();
}
