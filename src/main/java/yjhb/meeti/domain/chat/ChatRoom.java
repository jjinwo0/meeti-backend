package yjhb.meeti.domain.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yjhb.meeti.domain.user.entity.User;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class ChatRoom {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long id;

    private String roomName;

    @Min(2) @Max(50)
    private int maxCount;

    @OneToMany(mappedBy = "chatRoom", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ChatUser> chatUsers = new ArrayList<>();

    @Builder
    public ChatRoom(String roomName, int maxCount, User admin) {

        this.roomName = roomName;
        this.maxCount = maxCount;
        chatUsers.add(
                ChatUser.builder()
                        .user(admin)
                        .chatRoom(this)
                        .role(RoomRole.ADMIN)
                        .build()
        );
    }

    public void checkEnoughCount() {

        if (!(chatUsers.size() < maxCount)) {

            throw new RuntimeException();
        }
    }

    public void addUser(User user){

        checkEnoughCount();
        checkUserAtRoom(user.getId());
        chatUsers.add(
                ChatUser.builder()
                        .chatRoom(this)
                        .user(user)
                        .role(RoomRole.NORMAL)
                        .build()
        );
    }

    public void addUser(User user, RoomRole role){

        checkEnoughCount();
        checkUserAtRoom(user.getId());
        chatUsers.add(
                ChatUser.builder()
                        .chatRoom(this)
                        .user(user)
                        .role(role)
                        .build()
        );
    }

    public void removeUser(Long userId) {

        ChatUser chatUser = findUsers(userId);
        chatUsers.remove(chatUser);
    }

    public ChatUser findUsers(Long userId) {

        return chatUsers.stream()
                .filter(u -> u.getUser().getId().equals(userId))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }

    public void checkUserAtRoom(Long userId){

        chatUsers.stream()
                .filter(u -> u.getUser().getId().equals(userId))
                .findFirst()
                .orElseThrow(RuntimeException::new);
    }
}
