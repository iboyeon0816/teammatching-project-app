package com.sphere.demo.converter.project;

import com.sphere.demo.domain.Project;
import com.sphere.demo.domain.User;
import com.sphere.demo.domain.enums.ProjectState;
import com.sphere.demo.web.dto.ProjectRequestDto;
import com.sphere.demo.web.dto.ProjectRequestDto.CreateDto;
import com.sphere.demo.web.dto.ProjectResponseDto;

import java.util.ArrayList;

import static com.sphere.demo.web.dto.ProjectResponseDto.*;

public class ProjectConverter {

    public static Project toProject(CreateDto createDto) {
        return Project.builder()
                .title(createDto.getTitle())
                .body(createDto.getBody())
                .startDate(createDto.getStartDate())
                .endDate(createDto.getEndDate())
                .view(0) // 기본값
                .status(ProjectState.RECRUITMENT) // 기본값
                .deadline(createDto.getDeadline())
                .projectMatchList(new ArrayList<>())
                .projectPlatformList(new ArrayList<>())
                .projectRecruitPositionList(new ArrayList<>())
                .projectTechStackList(new ArrayList<>())
                .build();
    }

    public static CreateResultDto toCreateResultDto(Project project) {
        return new CreateResultDto(project.getId());
    }
}
