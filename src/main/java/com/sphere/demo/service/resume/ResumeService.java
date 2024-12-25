package com.sphere.demo.service.resume;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.converter.user.ResumeConverter;
import com.sphere.demo.domain.Position;
import com.sphere.demo.domain.Resume;
import com.sphere.demo.domain.ResumeTechnology;
import com.sphere.demo.domain.User;
import com.sphere.demo.exception.ex.PositionException;
import com.sphere.demo.exception.ex.ResumeException;
import com.sphere.demo.exception.ex.UserException;
import com.sphere.demo.repository.PositionRepository;
import com.sphere.demo.repository.ResumeRepository;
import com.sphere.demo.repository.UserRepository;
import com.sphere.demo.web.dto.user.ResumeRequestDto.ResumeDetailDto;
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
    private final PositionRepository positionRepository;

    public Resume addResume(Long userId, ResumeDetailDto resumeDetailDto){
        Resume resume = ResumeConverter.toResume(resumeDetailDto);
        setAssociations(resumeDetailDto, resume);
        setUserToResume(userId, resume);
        return resumeRepository.save(resume);
    }

    public void update(Long userId, Long resumeId, ResumeDetailDto resumeDetailDto) {
        Resume resume = validateAndFetchResume(userId, resumeId);
        resume.getResumeTechnologySet().clear();
        resume.update(resumeDetailDto);
        setAssociations(resumeDetailDto, resume);
    }

    public void delete(Long userId, Long resumeId) {
        Resume resume = validateAndFetchResume(userId, resumeId);
        resumeRepository.delete(resume);
    }

    private void setAssociations(ResumeDetailDto resumeDetailDto, Resume resume) {
        setTechnologyToResume(resumeDetailDto, resume);
        setPositionToResume(resumeDetailDto, resume);
    }

    private void setTechnologyToResume(ResumeDetailDto resumeDetailDto, Resume resume) {
        List<ResumeTechnology> resumeTechnologyList = resumeDetailDto.getTechnologyNameList().stream().map(
                ResumeTechnology::new
        ).toList();
        resumeTechnologyList.forEach(technology -> technology.setResume(resume));
    }

    private void setPositionToResume(ResumeDetailDto resumeDetailDto, Resume resume) {
        Position position = positionRepository.findById(resumeDetailDto.getPositionId())
                .orElseThrow(() -> new PositionException(ErrorStatus.POSITION_NOT_FOUND));
        resume.setPosition(position);
    }

    private void setUserToResume(Long userId, Resume resume) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));
        resume.setUser(user);
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
