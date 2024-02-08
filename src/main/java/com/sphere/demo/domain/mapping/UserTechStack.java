package com.sphere.demo.domain.mapping;
import com.sphere.demo.domain.TechnologyStack;
import com.sphere.demo.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserTechStack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "techStack_id")
    private TechnologyStack technologyStack;

    public void setUser(User user) {
        if (this.user != null) {
            this.user.getUserTechStackList().remove(this);
        }

        this.user = user;
        user.getUserTechStackList().add(this);
    }

    public void deleteTechStack(){
        if (this.user != null && this.technologyStack != null) {
            this.user.getUserPositionList().remove(this);
            this.technologyStack.getProjectTechStackList().remove(this);
            this.user = null;
            this.technologyStack = null;
        }
    }
}
