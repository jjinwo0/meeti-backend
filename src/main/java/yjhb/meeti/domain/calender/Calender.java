package yjhb.meeti.domain.calender;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yjhb.meeti.domain.user.entity.User;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Calender {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calender_id")
    private Long id;
    private String title;
    private String color;
    private String start;
    private String initTime;
    private String end;
    private String finishTime;
    private String place;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Calender(String title, String color, String start, String initTime, String end, String finishTime, String place, User user) {
        this.title = title;
        this.color = color;
        this.start = start;
        this.initTime = initTime;
        this.end = end;
        this.finishTime = finishTime;
        this.place = place;
        this.user = user;
    }

    public Calender update(String title, String color, String start, String initTime, String end, String finishTime, String place, User user) {
        this.title = title;
        this.color = color;
        this.start = start;
        this.initTime = initTime;
        this.end = end;
        this.finishTime = finishTime;
        this.place = place;
        this.user = user;

        return this;
    }
}
