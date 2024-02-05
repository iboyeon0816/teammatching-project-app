package com.sphere.demo.domain.mapping;

import com.sphere.demo.domain.Community;
import com.sphere.demo.domain.Position;
import com.sphere.demo.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserCommunity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id")
    private Community community;

    public void setUser(User user) {
        if (this.user != null) {
            this.user.getUserCommunityList().remove(this);
        }

        this.user = user;
        user.getUserCommunityList().add(this);
    }
}
