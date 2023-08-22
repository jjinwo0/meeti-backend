package yjhb.meeti.domain.reservation.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yjhb.meeti.domain.office.entity.Office;
import yjhb.meeti.domain.kakaopay.entity.KakaoPay;
import yjhb.meeti.domain.user.entity.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;
    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDateTime date;
    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime startTime;
    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime endTime;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "office_id")
    private Office office;

    @OneToOne(mappedBy = "payment_id")
    private KakaoPay payment;

    @Builder
    public Reservation(LocalDateTime date, LocalDateTime startTime, LocalDateTime endTime, User user, Office office) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.user = user;
        this.office = office;
    }
}
