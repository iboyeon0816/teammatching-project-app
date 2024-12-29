package com.sphere.demo.domain.mapping;
import com.sphere.demo.domain.Platform;
import com.sphere.demo.domain.Project;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjectPlatform {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "platform_id")
    private Platform platform;

    public ProjectPlatform(Platform platform) {
        this.platform = platform;
    }

    public void setProject(Project project) {
        if (this.project != null) {
            throw new IllegalStateException();
        }
        this.project = project;
        project.getProjectPlatformSet().add(this);
    }
}
