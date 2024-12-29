package com.sphere.demo.converter.resume;

import com.sphere.demo.domain.Resume;
import com.sphere.demo.domain.ResumeTechnology;

import java.util.List;

public class ResumeTechnologyConverter {
    public static void toResumeTechnology(List<String> technologyNameList, Resume resume) {
        technologyNameList.forEach(name -> {
            ResumeTechnology resumeTechnology = new ResumeTechnology(name);
            resumeTechnology.setResume(resume);
        });
    }
}
