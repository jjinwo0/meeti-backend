package yjhb.meeti.domain.calender.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yjhb.meeti.domain.user.entity.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Calender {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calender_id")
    private Long id;
    private String title;
    private String color;
    private LocalDateTime start;
    private LocalDateTime end;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Calender(User user, String title, String color, LocalDateTime start, LocalDateTime end) {
        this.user = user;
        this.title = title;
        this.color = color;
        this.start = start;
        this.end = end;
        user.getCalenders().add(this);
    }
}
