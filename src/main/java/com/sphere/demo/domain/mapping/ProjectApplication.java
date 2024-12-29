package com.sphere.demo.domain.mapping;

import com.sphere.demo.domain.ResumeSnapshot;
import com.sphere.demo.domain.User;
import com.sphere.demo.domain.common.BaseEntity;
import com.sphere.demo.domain.enums.ApplicationState;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectApplication extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ApplicationState state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_position_id", nullable = false)
    private ProjectPosition projectPosition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_snapshot_id", nullable = false)
    private ResumeSnapshot resumeSnapshot;

    @Builder
    public ProjectApplication(ResumeSnapshot resumeSnapshot) {
        this.state = ApplicationState.PENDING;
        this.resumeSnapshot = resumeSnapshot;
    }

    public void setUser(User user) {
        if (this.user != null) {
            throw new IllegalStateException();
        }
        this.user = user;
        user.getProjectApplicationList().add(this);
    }

    public void setProjectPosition(ProjectPosition projectPosition) {
        if (this.projectPosition != null) {
            throw new IllegalStateException();
        }
        this.projectPosition = projectPosition;
        projectPosition.getProjectApplicationList().add(this);
    }

    public void setState(ApplicationState state) {
        this.state = state;
    }
}
