package com.sphere.demo.converter.project;

import com.sphere.demo.domain.Project;
import com.sphere.demo.domain.enums.ProjectState;
import com.sphere.demo.domain.mapping.ProjectRecruitPosition;
import com.sphere.demo.web.dto.ProjectRequestDto.CreateDto;
import com.sphere.demo.web.dto.ProjectResponseDto.PositionInfo;
import com.sphere.demo.web.dto.ProjectResponseDto.ProjectInfoDto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.sphere.demo.web.dto.ProjectResponseDto.CreateResultDto;

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

    public static ProjectInfoDto toProjectInfoDto(Project project) {

        List<String> platformList = extractPlatformName(project);
        List<String> techStackList = extractTechStackName(project);
        List<PositionInfo> positionInfoList = extractPositionInfo(project);

        return ProjectInfoDto.builder()
                .title(project.getTitle())
                .body(project.getBody())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .view(project.getView())
                .status(project.getStatus())
                .createdAt(LocalDate.from(project.getCreatedAt()))
                .deadline(project.getDeadline())
                .totalNumber(calculateTotalNum(positionInfoList))
                .nickname(project.getUser().getNickname())
                .platformList(platformList)
                .techStackList(techStackList)
                .positionInfoList(positionInfoList)
                .build();
    }

    private static Integer calculateTotalNum(List<PositionInfo> positionInfoList) {
        Integer sum = 0;
        for (PositionInfo positionInfo : positionInfoList) {
            sum += positionInfo.getTotalNumber();
        }
        return sum;
    }

    public static PositionInfo toPositionInfo(ProjectRecruitPosition projectPosition) {
        return PositionInfo.builder()
                .positionName(projectPosition.getPosition().getName())
                .totalNumber(projectPosition.getMemberCount())
                .recruitedNumber(0) // TODO: 수정 필요
                .build();
    }

    private static List<PositionInfo> extractPositionInfo(Project project) {
        return project.getProjectRecruitPositionList().stream().map(
                ProjectConverter::toPositionInfo
        ).toList();
    }

    private static List<String> extractPlatformName(Project project) {
        return project.getProjectPlatformList().stream().map(
                projectPlatform -> projectPlatform.getPlatform().getName()
        ).toList();
    }

    private static List<String> extractTechStackName(Project project) {
        return project.getProjectTechStackList().stream().map(
                projectTechStack -> projectTechStack.getTechnologyStack().getName()
        ).toList();
    }
}
