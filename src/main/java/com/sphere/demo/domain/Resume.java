package com.sphere.demo.domain;

import com.sphere.demo.domain.common.BaseEntity;
import com.sphere.demo.web.dto.resume.ResumeRequestDto.ResumeDetailDto;
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
public class Resume extends BaseEntity {
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_id", nullable = false)
    private Position position;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ResumeTechnology> resumeTechnologySet;

    @Builder
    public Resume(String email, String selfIntroduction) {
        this.email = email;
        this.selfIntroduction = selfIntroduction;
        this.resumeTechnologySet = new HashSet<>();
    }

    public void setUser(User user) {
        if (this.user != null) {
            throw new IllegalStateException();
        }
        this.user = user;
        user.getResumeList().add(this);
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void update(ResumeDetailDto resumeDetailDto) {
        this.email = resumeDetailDto.getEmail();
        this.selfIntroduction = resumeDetailDto.getSelfIntroduction();
    }
}
