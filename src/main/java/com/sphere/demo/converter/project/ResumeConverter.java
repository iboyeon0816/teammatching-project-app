package com.sphere.demo.converter.project;

import com.sphere.demo.domain.Resume;
import com.sphere.demo.domain.ResumeSnapshot;

public class ResumeConverter {

    public static ResumeSnapshot toResumeSnapshot(Resume resume) {
        return ResumeSnapshot.builder()
                .email(resume.getEmail())
                .selfIntroduction(resume.getAboutMe())
                .user(resume.getUser())
                .build();
    }
}
