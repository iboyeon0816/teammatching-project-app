package com.sphere.demo.converter.project;

import com.sphere.demo.domain.Project;
import com.sphere.demo.domain.User;
import com.sphere.demo.web.dto.project.ProjectRequestDto.ProjectDetailDto;
import com.sphere.demo.web.dto.project.ProjectResponseDto.GetDetailDto;
import com.sphere.demo.web.dto.project.ProjectResponseDto.PositionDetailDto;
import com.sphere.demo.web.dto.project.ProjectResponseDto.ProjectCardDto;
import com.sphere.demo.web.dto.project.ProjectResponseDto.ProjectPageDto;
import org.springframework.data.domain.Page;

import java.util.List;

public class ProjectConverter {

    public static Project toProject(ProjectDetailDto projectDetailDto, User user) {
        Project project = Project.builder()
                .title(projectDetailDto.getTitle())
                .body(projectDetailDto.getBody())
                .startDate(projectDetailDto.getStartDate())
                .endDate(projectDetailDto.getEndDate())
                .deadline(projectDetailDto.getDeadline())
                .build();
        project.setUser(user);
        return project;
    }

    public static ProjectPageDto toProjectPageDto(Page<Project> projectPage, Long userId) {
        List<ProjectCardDto> projectCardDtoList = projectPage.getContent().stream()
                .map(project -> ProjectConverter.toProjectCardDto(project, userId))
                .toList();

        return ProjectPageDto.builder()
                .isFirst(projectPage.isFirst())
                .isLast(projectPage.isLast())
                .totalPages(projectPage.getTotalPages())
                .totalElements(projectPage.getTotalElements())
                .listSize(projectCardDtoList.size())
                .projectCardDtoList(projectCardDtoList)
                .build();
    }

    public static ProjectCardDto toProjectCardDto(Project project, Long userId) {
        return ProjectCardDto.builder()
                .projectId(project.getId())
                .title(project.getTitle())
                .imageUrl(project.getImagePath())
                .projectState(project.getStatus())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .viewCount(project.getView())
                .favoriteCount(project.getFavoriteCount())
                .isFavorite(project.isFavorite(userId))
                .positionList(getPositionList(project))
                .techNameList(project.getTechNameList())
                .build();
    }

    public static GetDetailDto toProjectDetailDto(Project project, Long userId) {
        return GetDetailDto.builder()
                .title(project.getTitle())
                .writerNickname(project.getUser().getNickname())
                .writerEmail(project.getUser().getUnivEmail())
                .body(project.getBody())
                .imageUrl(project.getImagePath())
                .projectState(project.getStatus())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .deadline(project.getDeadline())
                .viewCount(project.getView())
                .favoriteCount(project.getFavoriteCount())
                .isFavorite(project.isFavorite(userId))
                .isOwner(project.isOwner(userId))
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .platformNameList(project.getPlatformNameList())
                .techNameList(project.getTechNameList())
                .positionList(getPositionList(project))
                .build();
    }

    private static List<PositionDetailDto> getPositionList(Project project) {
        return project.getProjectPositionSet().stream()
                .map(ProjectPositionConverter::toPositionDetailDto)
                .toList();
    }
}