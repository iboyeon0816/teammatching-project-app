package com.sphere.demo.service.project;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.domain.Project;
import com.sphere.demo.exception.ex.ProjectException;
import com.sphere.demo.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProjectQueryServiceImpl implements ProjectQueryService {

    private final ProjectRepository projectRepository;

    @Override
    public Project findProject(Long projectId) {
        return projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectException(ErrorStatus.PROJECT_NOT_FOUND));
    }
}
