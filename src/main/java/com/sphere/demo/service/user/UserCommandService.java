package com.sphere.demo.service.user;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.domain.User;
import com.sphere.demo.exception.ex.UserException;
import com.sphere.demo.repository.UserRepository;
import com.sphere.demo.service.common.FileService;
import com.sphere.demo.service.common.enums.ImageType;
import com.sphere.demo.web.dto.user.UserAuthRequestDto.JoinDto;
import com.sphere.demo.web.dto.user.UserInfoRequestDto.UpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class UserCommandService {

    private final FileService fileService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void join(JoinDto joinDto) {
        validateNickname(joinDto.getNickname());
        String encodedPassword = passwordEncoder.encode(joinDto.getPassword().trim());
        User user = new User(joinDto, encodedPassword);
        userRepository.save(user);
    }

    public void updateUser(Long userId, UpdateDto updateDto) {
        User user = userRepository.findById(userId).orElseThrow(IllegalStateException::new);
        user.update(updateDto);
    }

    public void uploadImage(Long userId, MultipartFile file) {
        User user = userRepository.findById(userId).orElseThrow(IllegalStateException::new);
        String fileName = fileService.saveImage(file, ImageType.USER);
        user.setImagePath(fileName);
    }

    public void deleteImage(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(IllegalStateException::new);
        fileService.deleteFile(user.getImagePath(), ImageType.USER);
        user.setImagePath(null);
    }

    private void validateNickname(String nickname) {
        boolean exists = userRepository.existsByNickname(nickname);
        if (exists) {
            throw new UserException(ErrorStatus.DUPLICATED_NICKNAME);
        }
    }
}
