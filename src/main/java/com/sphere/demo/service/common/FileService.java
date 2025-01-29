package com.sphere.demo.service.common;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.exception.ex.ProjectException;
import com.sphere.demo.service.common.enums.ImageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    private final Environment environment;

    public String saveImage(MultipartFile file, ImageType imageType) {
        validateImageFile(file);
        String fileDir = imageType.getDirectory(environment);
        return saveFile(file, fileDir);
    }

    public void deleteFile(String imagePath, ImageType imageType) {
        String fileDir = imageType.getDirectory(environment);
        if (imagePath != null && !imagePath.isEmpty()) {
            File file = new File(fileDir + imagePath);
            if (file.exists()) {
                if (!file.delete()) {
                    log.info("File is not deleted: {}", imagePath);
                }
            }
        }
    }

    private static void validateImageFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new ProjectException(ErrorStatus.EMPTY_FILE);
        }

        String contentType = file.getContentType();
        if (contentType != null && !contentType.startsWith("image/")) {
            throw new ProjectException(ErrorStatus.INVALID_CONTENT_TYPE);
        }
    }

    private String saveFile(MultipartFile file, String fileDir) {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(fileDir + fileName);
        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ProjectException(ErrorStatus.FILE_UPLOAD_FAILED);
        }
        return fileName;
    }
}
