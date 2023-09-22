package yjhb.meeti.domain.user.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Friend {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friend_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friends")
    private User user;

    private Long fId;

    private boolean favorite;

    @Builder
    public Friend(User user, Long fId, boolean favorite) {
        this.user = user;
        this.fId = fId;
        this.favorite = favorite;
    }
}
