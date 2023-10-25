package yjhb.meeti.domain.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import yjhb.meeti.domain.user.entity.User;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    private String title;

    private String content;

    private boolean deletedBySender;

    private boolean deletedByReceiver;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION) // Sender or Receiver가 지우면 함께 지우기 위함
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private User receiver;

    public void deleteBySender() {
        this.deletedBySender = true;
    }

    public void deleteByReceiver() {
        this.deletedByReceiver = true;
    }

    public boolean isDeleted() { // 삭제 여부 판단 (둘 중에 하나라도 삭제되었으면 false)
        return isDeletedBySender() && isDeletedByReceiver();
    }
}
