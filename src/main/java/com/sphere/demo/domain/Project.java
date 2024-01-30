package com.sphere.demo.domain;

import com.sphere.demo.domain.enums.ProjectState;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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

}
