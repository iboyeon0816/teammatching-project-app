package com.sphere.demo.service.resume;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.converter.user.ResumeConverter;
import com.sphere.demo.domain.Resume;
import com.sphere.demo.domain.User;
import com.sphere.demo.exception.ex.ResumeException;
import com.sphere.demo.exception.ex.UserException;
import com.sphere.demo.repository.ResumeRepository;
import com.sphere.demo.repository.UserRepository;
import com.sphere.demo.web.dto.resume.ResumeRequestDto.ResumeDetailDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class ResumeService {

    private final UserRepository userRepository;
    private final ResumeRepository resumeRepository;
    private final ResumeAssociationHelper associationHelper;

    public Resume addResume(Long userId, ResumeDetailDto resumeDetailDto){
        User user = fetchUser(userId);
        Resume resume = ResumeConverter.toResume(resumeDetailDto, user);
        associationHelper.setAssociations(resumeDetailDto, resume);
        return resumeRepository.save(resume);
    }

    public void update(Long userId, Long resumeId, ResumeDetailDto resumeDetailDto) {
        Resume resume = validateAndFetchResume(userId, resumeId);
        resume.getResumeTechnologySet().clear();
        resume.update(resumeDetailDto);
        associationHelper.setAssociations(resumeDetailDto, resume);
    }

    public void delete(Long userId, Long resumeId) {
        Resume resume = validateAndFetchResume(userId, resumeId);
        resumeRepository.delete(resume);
    }

    private User fetchUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));
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
