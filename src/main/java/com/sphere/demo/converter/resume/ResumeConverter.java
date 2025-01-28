package com.sphere.demo.converter.resume;

import com.sphere.demo.domain.Resume;
import com.sphere.demo.domain.User;
import com.sphere.demo.web.dto.resume.ResumeRequestDto.ResumeDetailDto;

public class ResumeConverter {
    public static Resume toResume(ResumeDetailDto resumeDetailDto, User user){
        Resume resume = Resume.builder()
                .email(resumeDetailDto.getEmail())
                .selfIntroduction(resumeDetailDto.getSelfIntroduction())
                .build();
        resume.setUser(user);
        return resume;
    }
}
