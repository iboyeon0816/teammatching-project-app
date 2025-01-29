package com.sphere.demo.domain;

import com.sphere.demo.domain.common.BaseEntity;
import com.sphere.demo.domain.mapping.ProjectApplication;
import com.sphere.demo.domain.mapping.ProjectFavorite;
import com.sphere.demo.web.dto.user.UserAuthRequestDto.JoinDto;
import com.sphere.demo.web.dto.user.UserInfoRequestDto.UpdateDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String univEmail;

    @Column(nullable = false)
    private String univName;

    private String contactEmail;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    private String selfIntroduction;

    private String imagePath;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Project> projectList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Resume> resumeList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ProjectApplication> projectApplicationList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ProjectFavorite> projectFavoriteList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Community> communityList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> postList = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserRefreshToken userRefreshToken;

    public User(JoinDto joinDto, String password) {
        this.univEmail = joinDto.getUnivEmail();
        this.univName = joinDto.getUnivName();
        this.password = password;
        this.nickname = joinDto.getNickname();
        this.selfIntroduction = joinDto.getSelfIntroduction();
    }

    public void setUserRefreshToken(UserRefreshToken userRefreshToken) {
        this.userRefreshToken = userRefreshToken;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void update(UpdateDto updateDto) {
        this.contactEmail = updateDto.getContactEmail();
        this.selfIntroduction = updateDto.getSelfIntroduction();
    }
}
