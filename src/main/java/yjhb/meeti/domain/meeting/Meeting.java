package yjhb.meeti.domain.meeting;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yjhb.meeti.domain.user.entity.User;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Meeting {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meeting_id")
    private Long id;

    private String title;

    private String detail;

    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Meeting(String title, String detail, LocalDate date, User user) {
        this.title = title;
        this.detail = detail;
        this.date = date;
        this.user = user;
    }

    public Meeting update(String title, String detail, LocalDate date, User user){
        this.title = title;
        this.detail = detail;
        this.date = date;
        this.user = user;

        return this;
    }
}
