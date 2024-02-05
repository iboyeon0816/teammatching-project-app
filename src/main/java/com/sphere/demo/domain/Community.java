package com.sphere.demo.domain;

import com.sphere.demo.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import com.sphere.demo.domain.mapping.CommunityComment;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Community extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    private String body;

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL)
    private List<CommunityComment> communityCommentList = new ArrayList<>();


    public void setUser(User user) {
        if (this.user != null) {
            throw new IllegalStateException();
        }
        this.user = user;
        user.getCommunityList().add(this);
    }

}
