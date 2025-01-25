package com.sphere.demo.domain;

import com.sphere.demo.domain.common.BaseEntity;
import com.sphere.demo.domain.mapping.ProjectApplication;
import com.sphere.demo.domain.mapping.ProjectFavorite;
import jakarta.persistence.*;
import lombok.*;

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
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column
    private String selfIntroduction;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Project> projectList; // 작성한 프로젝트 리스트

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Resume> resumeList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ProjectApplication> projectApplicationList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ProjectFavorite> projectFavoriteList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Community> communityList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> postList = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserRefreshToken userRefreshToken;

    @Builder
    public User(String email, String nickname, String selfIntroduction) {
        this.email = email;
        this.nickname = nickname;
        this.selfIntroduction = selfIntroduction;
        this.projectList = new ArrayList<>();
        this.resumeList = new ArrayList<>();
        this.projectApplicationList = new ArrayList<>();
        this.projectFavoriteList = new ArrayList<>();
    }

    public void setUserRefreshToken(UserRefreshToken userRefreshToken) {
        this.userRefreshToken = userRefreshToken;
    }

    public void setPassword(String encodedPassword) {
        this.password = encodedPassword;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void update(String email, String selfIntroduction) {
        this.email = email;
        this.selfIntroduction = selfIntroduction;
    }
}
