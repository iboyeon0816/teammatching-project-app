package com.sphere.demo.service.common;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.exception.ex.ProjectException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
public class FileService {

    @Value("${file.dir}")
    private String FILE_DIR;

    public String saveImage(MultipartFile file) {
        validateImageFile(file);
        return saveFile(file);
    }

    public void deleteFile(String imagePath) {
        if (imagePath != null && !imagePath.isEmpty()) {
            File file = new File(FILE_DIR + imagePath);
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

    private String saveFile(MultipartFile file) {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(FILE_DIR + fileName);
        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ProjectException(ErrorStatus.FILE_UPLOAD_FAILED);
        }
        return fileName;
    }
}
