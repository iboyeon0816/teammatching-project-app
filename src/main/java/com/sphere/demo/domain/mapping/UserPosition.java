package com.sphere.demo.domain.mapping;
import com.sphere.demo.domain.Position;
import com.sphere.demo.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "position_id")
    private Position position;

    public void setUser(User user) {
        if (this.user != null) {
            this.user.getUserPositionList().remove(this);
        }
        this.user = user;
        user.getUserPositionList().add(this);
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void deletePosition(){
        if (this.user != null && this.position != null) {
            this.user.getUserPositionList().remove(this);
            this.position.getUserPositionList().remove(this);
            this.user = null;
            this.position = null;
        }
    }
}
