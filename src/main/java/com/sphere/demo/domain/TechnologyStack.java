package com.sphere.demo.domain;
import com.sphere.demo.domain.mapping.ProjectTechStack;
import com.sphere.demo.domain.mapping.UserTechStack;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TechnologyStack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "technologyStack", cascade = CascadeType.ALL)
    private List<UserTechStack> userTechStackList = new ArrayList<>();

    @OneToMany(mappedBy = "technologyStack", cascade = CascadeType.ALL)
    private List<ProjectTechStack> projectTechStackList = new ArrayList<>();
}
