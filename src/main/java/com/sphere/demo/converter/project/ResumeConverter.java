package com.sphere.demo.converter.project;

import com.sphere.demo.domain.Resume;
import com.sphere.demo.domain.ResumeSnapshot;
import com.sphere.demo.domain.ResumeSnapshotTechnology;
import com.sphere.demo.domain.ResumeTechnology;

public class ResumeConverter {

    public static ResumeSnapshot toResumeSnapshot(Resume resume) {
        return ResumeSnapshot.builder()
                .email(resume.getEmail())
                .selfIntroduction(resume.getSelfIntroduction())
                .user(resume.getUser())
                .position(resume.getPosition())
                .build();
    }

    public static ResumeSnapshotTechnology toResumeSnapshotTechnology(ResumeTechnology resumeTechnology) {
        return ResumeSnapshotTechnology.builder()
                .name(resumeTechnology.getName())
                .build();
    }

}
