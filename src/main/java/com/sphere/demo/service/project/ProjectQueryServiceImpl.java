package com.sphere.demo.service.project;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.domain.Project;
import com.sphere.demo.exception.ex.ProjectException;
import com.sphere.demo.repository.ProjectQueryDslRepository;
import com.sphere.demo.web.dto.project.ProjectRequestDto.ProjectSearchCond;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProjectQueryServiceImpl implements ProjectQueryService {

    private static final int DEFAULT_PAGE_SIZE = 8;

    private final ProjectQueryDslRepository projectQueryDslRepository;

    @Override
    public Page<Project> getProjects(ProjectSearchCond projectSearchCond, Integer page) {
        return projectQueryDslRepository.findAll(projectSearchCond, PageRequest.of(page, DEFAULT_PAGE_SIZE));
    }

    @Override
    public Project getProject(Long projectId) {
        return projectQueryDslRepository.findDetailById(projectId)
                .orElseThrow(() -> new ProjectException(ErrorStatus.PROJECT_NOT_FOUND));
    }

    @Override
    public List<Project> getProjectWithMostViews() {
        return projectQueryDslRepository.findNewProjects(true);
    }

    @Override
    public List<Project> getNewProject() {
        return projectQueryDslRepository.findNewProjects(false);
    }

}
