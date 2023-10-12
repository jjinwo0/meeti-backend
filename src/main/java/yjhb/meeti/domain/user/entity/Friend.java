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

    private Long fromId; // 요청을 보내는 user의 id

    private boolean favorite;

    private boolean permit;

    @Builder
    public Friend(User user, Long fromId, boolean favorite, boolean permit) {
        this.user = user;
        this.fromId = fromId;
        this.favorite = favorite;
        this.permit = permit;
    }

    public void acceptPermit(){

        this.permit = true;
    }

    public void refusePermit(){

        this.permit = false;
    }
}
