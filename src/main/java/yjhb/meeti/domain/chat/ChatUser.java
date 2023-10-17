package yjhb.meeti.domain.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yjhb.meeti.domain.user.entity.User;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ChatUser {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private ChatRoom chatRoom;

    private RoomRole role;

    @Builder
    public ChatUser(User user, ChatRoom chatRoom, RoomRole role) {
        this.user = user;
        this.chatRoom = chatRoom;
        this.role = role;
    }
}
