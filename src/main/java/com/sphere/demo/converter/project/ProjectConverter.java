package com.sphere.demo.converter.project;

import com.sphere.demo.domain.Project;
import com.sphere.demo.domain.enums.MatchState;
import com.sphere.demo.domain.enums.ProjectState;
import com.sphere.demo.domain.mapping.ProjectMatch;
import com.sphere.demo.domain.mapping.ProjectRecruitPosition;
import com.sphere.demo.web.dto.ProjectRequestDto.CreateDto;
import com.sphere.demo.web.dto.ProjectResponseDto.PositionDetailDto;
import com.sphere.demo.web.dto.ProjectResponseDto.ProjectDetailDto;
import com.sphere.demo.web.dto.ProjectResponseDto.ProjectDto;
import com.sphere.demo.web.dto.ProjectResponseDto.ProjectPageDto;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.HashSet;
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
                .projectPlatformSet(new HashSet<>())
                .projectRecruitPositionSet(new HashSet<>())
                .projectTechStackSet(new HashSet<>())
                .build();
    }

    public static CreateResultDto toCreateResultDto(Project project) {
        return new CreateResultDto(project.getId());
    }

    public static ProjectDetailDto toProjectDetailDto(Project project) {

        List<String> platformList = getPlatformName(project);
        List<String> techStackList = getTechStackName(project);
        List<PositionDetailDto> positionDetailDtoList = extractPositionInfo(project);

        return ProjectDetailDto.builder()
                .title(project.getTitle())
                .body(project.getBody())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .view(project.getView())
                .status(project.getStatus())
                .createdAt(LocalDate.from(project.getCreatedAt()))
                .deadline(project.getDeadline())
                .totalNumber(calculateTotalNum(positionDetailDtoList))
                .nickname(project.getUser().getNickname())
                .platformList(platformList)
                .techStackList(techStackList)
                .positionDetailDtoList(positionDetailDtoList)
                .build();
    }

    public static PositionDetailDto toPositionDetailDto(ProjectRecruitPosition projectPosition) {
        int matchCount = getMatchCount(projectPosition);
        return PositionDetailDto.builder()
                .positionName(projectPosition.getPosition().getName())
                .totalNumber(projectPosition.getMemberCount())
                .recruitedNumber(matchCount)
                .build();
    }

    public static ProjectDto toProjectDto(Project project) {

        return ProjectDto.builder()
                .creatorNickname(project.getUser().getNickname())
                .title(project.getTitle())
                .positionList(getPositionName(project))
                .techStackList(getTechStackName(project))
                .platformList(getPlatformName(project))
                .deadline(project.getDeadline())
                .views(project.getView())
                .projectState(project.getStatus())
                .build();
    }

    public static ProjectPageDto toProjectPageDto(Page<Project> projectPage) {
        List<ProjectDto> projectDtoList = projectPage.getContent().stream()
                .map(ProjectConverter::toProjectDto)
                .toList();

        return ProjectPageDto.builder()
                .isFirst(projectPage.isFirst())
                .isLast(projectPage.isLast())
                .totalPages(projectPage.getTotalPages())
                .totalElements(projectPage.getTotalElements())
                .listSize(projectDtoList.size())
                .projectDtoList(projectDtoList)
                .build();
    }

    private static Integer calculateTotalNum(List<PositionDetailDto> positionDetailDtoList) {
        Integer sum = 0;
        for (PositionDetailDto positionDetailDto : positionDetailDtoList) {
            sum += positionDetailDto.getTotalNumber();
        }
        return sum;
    }

    private static List<PositionDetailDto> extractPositionInfo(Project project) {
        return project.getProjectRecruitPositionSet().stream().map(
                ProjectConverter::toPositionDetailDto
        ).toList();
    }

    private static List<String> getPlatformName(Project project) {
        return project.getProjectPlatformSet().stream().map(
                projectPlatform -> projectPlatform.getPlatform().getName()
        ).toList();
    }

    private static List<String> getTechStackName(Project project) {
        return project.getProjectTechStackSet().stream().map(
                projectTechStack -> projectTechStack.getTechnologyStack().getName()
        ).toList();
    }

    private static List<String> getPositionName(Project project) {
        return project.getProjectRecruitPositionSet().stream().map(
                projectPosition -> projectPosition.getPosition().getName()
        ).toList();
    }

    private static int getMatchCount(ProjectRecruitPosition projectPosition) {
        int matchCount = 0;
        List<ProjectMatch> projectMatchList = projectPosition.getProjectMatchList();
        for (ProjectMatch projectMatch : projectMatchList) {
            if (projectMatch.getState() == MatchState.MATCH) {
                matchCount++;
            }
        }
        return matchCount;
    }
}
