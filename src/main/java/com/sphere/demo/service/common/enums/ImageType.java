package com.sphere.demo.service.common.enums;

import org.springframework.core.env.Environment;

public enum ImageType {
    PROJECT("file.dir.project"),
    USER("file.dir.user");

    private final String fileDir;

    ImageType(String fileDir) {
        this.fileDir = fileDir;
    }

    public String getDirectory(Environment environment) {
        return environment.getProperty(this.fileDir);
    }
}
