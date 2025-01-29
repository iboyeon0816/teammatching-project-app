package com.sphere.demo.service.resume;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.converter.resume.ResumeConverter;
import com.sphere.demo.domain.Resume;
import com.sphere.demo.domain.User;
import com.sphere.demo.exception.ex.ResumeException;
import com.sphere.demo.exception.ex.UserException;
import com.sphere.demo.repository.ResumeRepository;
import com.sphere.demo.repository.UserRepository;
import com.sphere.demo.web.dto.resume.ResumeRequestDto.ResumeDetailDto;
import com.sphere.demo.web.dto.resume.ResumeRequestDto.ResumeProjectDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class ResumeService {

    private final UserRepository userRepository;
    private final ResumeRepository resumeRepository;

    public Resume addResume(Long userId, ResumeDetailDto resumeDetailDto){
        User user = userRepository.findById(userId).orElseThrow(IllegalStateException::new);
        Resume resume = ResumeConverter.toResume(resumeDetailDto, user);
        setProjectAssociations(resumeDetailDto.getResumeProjectDetailDtoList(), resume);
        return resumeRepository.save(resume);
    }

    public void update(Long userId, Long resumeId, ResumeDetailDto resumeDetailDto) {
        Resume resume = validateAndFetchResume(userId, resumeId);
        resume.getResumeProjectList().clear();
        resume.update(resumeDetailDto);
        setProjectAssociations(resumeDetailDto.getResumeProjectDetailDtoList(), resume);
    }

    private void setProjectAssociations(List<ResumeProjectDetailDto> resumeProjectDtoList, Resume resume) {
        resumeProjectDtoList.forEach(resumeProjectDto -> ResumeConverter.toResumeProject(resumeProjectDto, resume));
    }

    private Resume validateAndFetchResume(Long userId, Long resumeId) {
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new ResumeException(ErrorStatus.RESUME_NOT_FOUND));

        if (!Objects.equals(resume.getUser().getId(), userId)) {
            throw new UserException(ErrorStatus._FORBIDDEN);
        }

        return resume;
    }
}
