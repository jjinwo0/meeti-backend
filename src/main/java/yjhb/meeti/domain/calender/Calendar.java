package yjhb.meeti.domain.calender;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yjhb.meeti.domain.user.entity.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Calendar {

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
    public Calendar(String title, String color, String start, String initTime, String end, String finishTime, String place, User user) {
        this.title = title;
        this.color = color;
        this.start = start;
        this.initTime = initTime;
        this.end = end;
        this.finishTime = finishTime;
        this.place = place;
        this.user = user;
    }
}
