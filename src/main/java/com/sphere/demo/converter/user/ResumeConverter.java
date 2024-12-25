package com.sphere.demo.converter.user;

import com.sphere.demo.domain.Resume;
import com.sphere.demo.web.dto.user.ResumeRequestDto.ResumeDetailDto;

public class ResumeConverter {
    public static Resume toResume(ResumeDetailDto resumeDetailDto){
        return Resume.builder()
                .email(resumeDetailDto.getEmail())
                .selfIntroduction(resumeDetailDto.getSelfIntroduction())
                .build();
    }
}
