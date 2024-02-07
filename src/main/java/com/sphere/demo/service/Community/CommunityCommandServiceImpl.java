package com.sphere.demo.service.Community;

import com.sphere.demo.apipayload.status.ErrorStatus;
import com.sphere.demo.converter.CommentConverter;
import com.sphere.demo.converter.CommunityConverter;
import com.sphere.demo.domain.Comment;
import com.sphere.demo.domain.Community;
import com.sphere.demo.domain.Project;
import com.sphere.demo.domain.User;
import com.sphere.demo.exception.ex.CommunityException;
import com.sphere.demo.exception.ex.UserException;
import com.sphere.demo.repository.CommentRepository;
import com.sphere.demo.repository.CommunityRepository;
import com.sphere.demo.repository.UserRepository;
import com.sphere.demo.web.dto.CommunityRequestDto;
import com.sphere.demo.web.dto.CommentRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityCommandServiceImpl implements CommunityCommandService {

    private final CommunityRepository communityRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    @Override
    public void create(Long userId, CommunityRequestDto.CreateDto createDto) {
        Community community = CommunityConverter.toCommunity(createDto);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));
        community.setUser(user);

        communityRepository.save(community);
    }

    @Override
    public void communityViewUp(Community community) {
        community.viewUp();
    }

    @Override
    public void createComment(Long userId, Long communityId, CommentRequestDto.CreateCommentDto createCommentDto) {
        Comment comment = CommunityConverter.toComment(createCommentDto);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));
        comment.setUser(user);

        Community community = communityRepository.findById(communityId)
               .orElseThrow(() -> new CommunityException(ErrorStatus.COMMUNITY_NOT_FOUND));
        comment.setCommunity(community);

        commentRepository.save(comment);
    }

}
