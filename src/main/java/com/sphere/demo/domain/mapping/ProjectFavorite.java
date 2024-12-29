package com.sphere.demo.domain.mapping;

import com.sphere.demo.domain.Project;
import com.sphere.demo.domain.User;
import com.sphere.demo.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectFavorite extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public ProjectFavorite(Project project) {
        this.project = project;
    }

    public void setUser(User user) {
        if (this.user != null) {
            throw new IllegalStateException();
        }
        this.user = user;
        user.getProjectFavoriteList().add(this);
    }
}
