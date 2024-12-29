package com.sphere.demo.service.resume;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.converter.resume.ResumeTechnologyConverter;
import com.sphere.demo.domain.Position;
import com.sphere.demo.domain.Resume;
import com.sphere.demo.exception.ex.PositionException;
import com.sphere.demo.repository.PositionRepository;
import com.sphere.demo.web.dto.resume.ResumeRequestDto.ResumeDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ResumeAssociationHelper {

    private final PositionRepository positionRepository;

    public void setAssociations(ResumeDetailDto resumeDetailDto, Resume resume) {
        setupTechnologies(resumeDetailDto, resume);
        setupPosition(resumeDetailDto, resume);
    }

    private void setupTechnologies(ResumeDetailDto resumeDetailDto, Resume resume) {
        List<String> technologyNameList = resumeDetailDto.getTechnologyNameList();
        ResumeTechnologyConverter.toResumeTechnology(technologyNameList, resume);
    }

    private void setupPosition(ResumeDetailDto resumeDetailDto, Resume resume) {
        Position position = positionRepository.findById(resumeDetailDto.getPositionId())
                .orElseThrow(() -> new PositionException(ErrorStatus.POSITION_NOT_FOUND));
        resume.setPosition(position);
    }
}
