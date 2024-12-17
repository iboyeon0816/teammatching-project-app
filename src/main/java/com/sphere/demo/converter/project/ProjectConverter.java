package com.sphere.demo.converter.project;

import com.sphere.demo.domain.Project;
import com.sphere.demo.domain.Technology;
import com.sphere.demo.domain.enums.MatchState;
import com.sphere.demo.domain.mapping.ProjectMatch;
import com.sphere.demo.domain.mapping.ProjectRecruitPosition;
import com.sphere.demo.web.dto.project.ProjectRequestDto.ProjectDetailDto;
import com.sphere.demo.web.dto.project.ProjectResponseDto;
import com.sphere.demo.web.dto.project.ProjectResponseDto.GetResultDto;
import com.sphere.demo.web.dto.project.ProjectResponseDto.PositionDetailDto;
import com.sphere.demo.web.dto.project.ProjectResponseDto.ProjectPageDto;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static com.sphere.demo.web.dto.project.ProjectResponseDto.CreateResultDto;

public class ProjectConverter {

    public static Project toProject(ProjectDetailDto projectDetailDto) {
        return Project.builder()
                .title(projectDetailDto.getTitle())
                .body(projectDetailDto.getBody())
                .startDate(projectDetailDto.getStartDate())
                .endDate(projectDetailDto.getEndDate())
                .deadline(projectDetailDto.getDeadline())
                .build();
    }

    public static CreateResultDto toCreateResultDto(Project project) {
        return new CreateResultDto(project.getId());
    }

    public static ProjectResponseDto.ProjectDetailDto toProjectDetailDto(Project project) {
        List<PositionDetailDto> positionDetailDtoList = project.getProjectRecruitPositionSet()
                .stream().map(ProjectConverter::toPositionDetailDto)
                .toList();

        return ProjectResponseDto.ProjectDetailDto.builder()
                .title(project.getTitle())
                .body(project.getBody())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .views(project.getView())
                .projectState(project.getStatus())
                .createdAt(LocalDate.from(project.getCreatedAt()))
                .deadline(project.getDeadline())
                .totalRecruitNumber(getTotalNum(project))
                .writerNickname(project.getUser().getNickname())
                .platformNameList(getPlatformNames(project))
                .techStackNameList(getTechStackNames(project))
                .positionDetailDtoList(positionDetailDtoList)
                .build();
    }

    private static PositionDetailDto toPositionDetailDto(ProjectRecruitPosition projectPosition) {
        int matchedNum = getMatchedNum(projectPosition);
        return PositionDetailDto.builder()
                .positionName(projectPosition.getPosition().getName())
                .totalNumber(projectPosition.getMemberCount())
                .matchedNumber(matchedNum)
                .build();
    }

    public static ProjectPageDto toProjectPageDto(Page<Project> projectPage) {
        List<GetResultDto> getResultDtoList = projectPage.getContent().stream()
                .map(ProjectConverter::toGetResultDto)
                .toList();

        return ProjectPageDto.builder()
                .isFirst(projectPage.isFirst())
                .isLast(projectPage.isLast())
                .totalPages(projectPage.getTotalPages())
                .totalElements(projectPage.getTotalElements())
                .listSize(getResultDtoList.size())
                .getResultDtoList(getResultDtoList)
                .build();
    }

    public static GetResultDto toGetResultDto(Project project) {

        return GetResultDto.builder()
                .projectId(project.getId())
                .writerNickname(project.getUser().getNickname())
                .title(project.getTitle())
                .positionNameList(getPositionNames(project))
                .techStackNameList(getTechStackNames(project))
                .platformNameList(getPlatformNames(project))
                .deadline(project.getDeadline())
                .views(project.getView())
                .projectState(project.getStatus())
                .build();
    }

    private static int getMatchedNum(ProjectRecruitPosition projectPosition) {
        int matchedCount = 0;
        List<ProjectMatch> projectMatchList = projectPosition.getProjectMatchList();
        for (ProjectMatch projectMatch : projectMatchList) {
            if (projectMatch.getState() == MatchState.MATCH) {
                matchedCount++;
            }
        }
        return matchedCount;
    }

    private static int getTotalNum(Project project) {
        int totalNum = 0;
        Set<ProjectRecruitPosition> projectRecruitPositionSet = project.getProjectRecruitPositionSet();
        for (ProjectRecruitPosition projectPosition : projectRecruitPositionSet) {
            totalNum += projectPosition.getMemberCount();
        }
        return totalNum;
    }

    private static List<String> getPlatformNames(Project project) {
        return project.getProjectPlatformSet().stream().map(
                projectPlatform -> projectPlatform.getPlatform().getName()
        ).toList();
    }

    private static List<String> getTechStackNames(Project project) {
        return project.getTechnologySet().stream().map(
                Technology::getName
        ).toList();
    }

    private static List<String> getPositionNames(Project project) {
        return project.getProjectRecruitPositionSet().stream().map(
                projectPosition -> projectPosition.getPosition().getName()
        ).toList();
    }
}