package com.sphere.demo.domain;

import com.sphere.demo.domain.common.BaseEntity;
import com.sphere.demo.web.dto.resume.ResumeRequestDto.ResumeDetailDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Resume extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String aboutMe;

    @Column(nullable = false)
    private String techStacks;

    private String awards;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<ResumeProject> resumeProjectList = new ArrayList<>();

    @Builder
    public Resume(String title, String name, LocalDate birthDate, String position, String email, String aboutMe, String techStacks, String awards) {
        this.title = title;
        this.name = name;
        this.birthDate = birthDate;
        this.position = position;
        this.email = email;
        this.aboutMe = aboutMe;
        this.techStacks = techStacks;
        this.awards = awards;
    }

    public void setUser(User user) {
        if (this.user != null) {
            throw new IllegalStateException();
        }
        this.user = user;
        user.getResumeList().add(this);
    }

    public void update(ResumeDetailDto resumeDetailDto) {
        this.title = resumeDetailDto.getTitle();
        this.name = resumeDetailDto.getName();
        this.birthDate = resumeDetailDto.getBirthDate();
        this.position = resumeDetailDto.getPosition();
        this.email = resumeDetailDto.getEmail();
        this.aboutMe = resumeDetailDto.getAboutMe();
        this.techStacks = resumeDetailDto.getTechStacks();
        this.awards = resumeDetailDto.getAwards();
    }
}
