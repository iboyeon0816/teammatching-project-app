package com.sphere.demo.service.project;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.domain.Position;
import com.sphere.demo.domain.Project;
import com.sphere.demo.domain.enums.MatchState;
import com.sphere.demo.domain.mapping.ProjectMatch;
import com.sphere.demo.domain.mapping.ProjectRecruitPosition;
import com.sphere.demo.exception.ex.PositionException;
import com.sphere.demo.exception.ex.ProjectException;
import com.sphere.demo.repository.PositionRepository;
import com.sphere.demo.repository.ProjectQueryDslRepository;
import com.sphere.demo.repository.ProjectRecruitPositionRepository;
import com.sphere.demo.repository.ProjectRepository;
import com.sphere.demo.web.dto.UserRequestDto.ApplyDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProjectQueryServiceImpl implements ProjectQueryService {

    private final ProjectRepository projectRepository;
    private final PositionRepository positionRepository;
    private final ProjectRecruitPositionRepository positionPositionRepository;
    private final ProjectQueryDslRepository projectQueryDslRepository;

    @Override
    public Project findProject(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectException(ErrorStatus.PROJECT_NOT_FOUND));
    }

    @Override
    public ProjectRecruitPosition findProjectPosition(Long projectId, ApplyDto applyDto) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectException(ErrorStatus.PROJECT_NOT_FOUND));

        Position position = positionRepository.findById(applyDto.getPositionId())
                .orElseThrow(() -> new PositionException(ErrorStatus.POSITION_NOT_FOUND));

        ProjectRecruitPosition projectPosition = positionPositionRepository.findByProjectAndPosition(project, position)
                .orElseThrow(() -> new ProjectException(ErrorStatus.NOT_RECRUITING_POSITION));

        int matchCount = 0;
        List<ProjectMatch> projectMatchList = projectPosition.getProjectMatchList();
        for (ProjectMatch projectMatch : projectMatchList) {
            if (projectMatch.getState() == MatchState.MATCH) {
                matchCount++;
            }
        }

        if (projectPosition.getMemberCount() <= matchCount) {
            throw new ProjectException(ErrorStatus.ALREADY_MATCHING_END_POSITION);
        }

        return projectPosition;
    }

    @Override
    public List<Project> findProjectWithMostViews() {
        return projectQueryDslRepository.findNewProject(true);
    }

    @Override
    public List<Project> findNewProject() {
        return projectQueryDslRepository.findNewProject(false);
    }
}
