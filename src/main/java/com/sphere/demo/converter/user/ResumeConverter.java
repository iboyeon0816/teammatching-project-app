package com.sphere.demo.converter.user;

import com.sphere.demo.domain.Resume;
import com.sphere.demo.web.dto.user.ResumeRequestDto.AddResumeDto;

public class ResumeConverter {
    public static Resume toResume(AddResumeDto addResumeDto){
        return Resume.builder()
                .email(addResumeDto.getEmail())
                .selfIntroduction(addResumeDto.getSelfIntroduction())
                .build();
    }
}
