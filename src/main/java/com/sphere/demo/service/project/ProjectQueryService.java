package com.sphere.demo.service.project;

import com.sphere.demo.domain.Project;
import com.sphere.demo.domain.mapping.ProjectRecruitPosition;
import com.sphere.demo.web.dto.UserRequestDto.ApplyDto;

public interface ProjectQueryService {
    Project findProject(Long projectId);

    ProjectRecruitPosition findProjectPosition(Long projectId, ApplyDto applyDto);
}
