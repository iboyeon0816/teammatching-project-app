package com.sphere.demo.converter.resume;

import com.sphere.demo.domain.Resume;
import com.sphere.demo.domain.ResumeProject;
import com.sphere.demo.domain.User;
import com.sphere.demo.web.dto.resume.ResumeRequestDto.ResumeDetailDto;
import com.sphere.demo.web.dto.resume.ResumeRequestDto.ResumeProjectDetailDto;

public class ResumeConverter {
    public static Resume toResume(ResumeDetailDto resumeDetailDto, User user){
        Resume resume = Resume.builder()
                .title(resumeDetailDto.getTitle())
                .name(resumeDetailDto.getName())
                .birthDate(resumeDetailDto.getBirthDate())
                .position(resumeDetailDto.getPosition())
                .email(resumeDetailDto.getEmail())
                .aboutMe(resumeDetailDto.getAboutMe())
                .techStacks(resumeDetailDto.getTechStacks())
                .awards(resumeDetailDto.getAwards())
                .build();
        resume.setUser(user);
        return resume;
    }

    public static void toResumeProject(ResumeProjectDetailDto resumeProjectDetailDto, Resume resume) {
        ResumeProject resumeProject = ResumeProject.builder()
                .title(resumeProjectDetailDto.getTitle())
                .startDate(resumeProjectDetailDto.getStartDate())
                .endDate(resumeProjectDetailDto.getEndDate())
                .techStacks(resumeProjectDetailDto.getTechStacks())
                .serviceContents(resumeProjectDetailDto.getServiceContents())
                .devDetails(resumeProjectDetailDto.getDevDetails())
                .growthDetails(resumeProjectDetailDto.getGrowthDetails())
                .relativeActivities(resumeProjectDetailDto.getRelativeActivities())
                .build();
        resumeProject.setResume(resume);
    }
}
