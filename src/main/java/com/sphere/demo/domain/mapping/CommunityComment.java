package com.sphere.demo.domain.mapping;

import com.sphere.demo.domain.Community;
import com.sphere.demo.domain.Comment;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommunityComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_id")
    private Community community;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public void setCommunity(Community community) {
        if (this.community != null) {
            this.community.getCommunityCommentList().remove(this);
        }

        this.community = community;
        community.getCommunityCommentList().add(this);
    }
}
