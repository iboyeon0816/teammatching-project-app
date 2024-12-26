package com.sphere.demo.domain;

import com.sphere.demo.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResumeSnapshot extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String selfIntroduction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", nullable = false)
    private Position position;

    @OneToMany(mappedBy = "resumeSnapshot", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ResumeSnapshotTechnology> resumeSnapshotTechnologySet;

    @Builder
    public ResumeSnapshot(String email, String selfIntroduction, User user, Position position, Set<ResumeSnapshotTechnology> resumeSnapshotTechnologySet) {
        this.email = email;
        this.selfIntroduction = selfIntroduction;
        this.user = user;
        this.position = position;
        this.resumeSnapshotTechnologySet = new HashSet<>();
    }

}
