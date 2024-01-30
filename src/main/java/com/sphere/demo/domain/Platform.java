package com.sphere.demo.domain;
import com.sphere.demo.domain.mapping.ProjectPlatform;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Platform {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "platform", cascade = CascadeType.ALL)
    private List<ProjectPlatform> projectPlatformList = new ArrayList<>();
}
