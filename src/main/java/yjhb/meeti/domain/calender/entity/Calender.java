package yjhb.meeti.domain.calender.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Calender {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String color;
    private Date start;
    private Date end;

    @Builder
    public Calender(String title, String color, Date start, Date end) {
        this.title = title;
        this.color = color;
        this.start = start;
        this.end = end;
    }
}
