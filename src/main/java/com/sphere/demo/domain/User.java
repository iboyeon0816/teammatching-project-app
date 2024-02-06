package com.sphere.demo.domain;

import com.sphere.demo.domain.common.BaseEntity;
import com.sphere.demo.domain.mapping.ProjectMatch;
import com.sphere.demo.domain.mapping.UserPosition;
import com.sphere.demo.domain.mapping.UserTechStack;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loginId;

    private String password;

    private String nickname;

    private String email;

    private String school;

    private String major;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ProjectMatch> projectMatchList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserPosition> userPositionList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserTechStack> userTechStackList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PortfolioProject> portfolioProject = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> postList = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserRefreshToken userRefreshToken;

    public void setUserRefreshToken(UserRefreshToken userRefreshToken) {
        this.userRefreshToken = userRefreshToken;
    }

    public void setUserPositionList(List<UserPosition> userPositionList) {
        this.userPositionList = userPositionList;
        if (userPositionList != null) {
            for (UserPosition userPosition : userPositionList) {
                userPosition.setUser(this);
            }
        }
    }
}
