package com.sphere.demo.domain;

import com.sphere.demo.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
public class UserRefreshToken extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false)
    private String refreshToken;

    public void setUser(User user) {
        if (this.user != null) {
            throw new IllegalStateException();
        }
        this.user = user;
        user.setUserRefreshToken(this);
    }

    public void setRefreshToken(String newRefreshToken) {
        this.refreshToken = newRefreshToken;
    }
}
