package com.sphere.demo.service.resume;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.converter.project.ResumeConverter;
import com.sphere.demo.domain.Resume;
import com.sphere.demo.domain.ResumeSnapshot;
import com.sphere.demo.domain.User;
import com.sphere.demo.exception.ex.ResumeException;
import com.sphere.demo.exception.ex.UserException;
import com.sphere.demo.repository.ResumeRepository;
import com.sphere.demo.repository.ResumeSnapshotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ResumeSnapshotService {

    private final ResumeRepository resumeRepository;
    private final ResumeSnapshotRepository resumeSnapshotRepository;

    public ResumeSnapshot saveSnapshot(User user, Long resumeId) {
        Resume resume = validateAndFetchResume(user, resumeId);
        ResumeSnapshot resumeSnapshot = ResumeConverter.toResumeSnapshot(resume);
        resumeSnapshotRepository.save(resumeSnapshot);
        return resumeSnapshot;
    }

    private Resume validateAndFetchResume(User user, Long resumeId) {
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new ResumeException(ErrorStatus.RESUME_NOT_FOUND));

        if (!resume.getUser().getId().equals(user.getId())){
            throw new UserException(ErrorStatus._FORBIDDEN);
        }

        return resume;
    }
}
