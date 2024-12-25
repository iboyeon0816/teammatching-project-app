package com.sphere.demo.service.user;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.converter.user.ResumeConverter;
import com.sphere.demo.domain.Position;
import com.sphere.demo.domain.Resume;
import com.sphere.demo.domain.ResumeTechnology;
import com.sphere.demo.domain.User;
import com.sphere.demo.exception.ex.PositionException;
import com.sphere.demo.exception.ex.UserException;
import com.sphere.demo.repository.PositionRepository;
import com.sphere.demo.repository.ResumeRepository;
import com.sphere.demo.repository.UserRepository;
import com.sphere.demo.web.dto.user.ResumeRequestDto.AddResumeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final UserRepository userRepository;
    private final ResumeRepository resumeRepository;
    private final PositionRepository positionRepository;

    public Resume addResume(Long userId, AddResumeDto addResumeDto){
        Resume resume = ResumeConverter.toResume(addResumeDto);
        setAssociations(userId, addResumeDto, resume);
        return resumeRepository.save(resume);
    }

    private void setAssociations(Long userId, AddResumeDto addResumeDto, Resume resume) {
        setTechnologyToResume(addResumeDto, resume);
        setPositionToResume(addResumeDto, resume);
        setUserToResume(userId, resume);
    }

    private void setTechnologyToResume(AddResumeDto addResumeDto, Resume resume) {
        List<ResumeTechnology> resumeTechnologyList = addResumeDto.getTechnologyNameList().stream().map(
                ResumeTechnology::new
        ).toList();
        resumeTechnologyList.forEach(technology -> technology.setResume(resume));
    }

    private void setPositionToResume(AddResumeDto addResumeDto, Resume resume) {
        Position position = positionRepository.findById(addResumeDto.getPositionId())
                .orElseThrow(() -> new PositionException(ErrorStatus.POSITION_NOT_FOUND));
        resume.setPosition(position);
    }

    private void setUserToResume(Long userId, Resume resume) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));
        resume.setUser(user);
    }
}
