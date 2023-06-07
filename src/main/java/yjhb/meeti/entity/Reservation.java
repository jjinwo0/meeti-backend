package yjhb.meeti.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yjhb.meeti.dto.ReservationDTO;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class Reservation {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;
    private String areaName;
    private String address;
    private int pay;
    private String tel;
    private String description;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Reservation(ReservationDTO dto, User user){
        this.areaName = dto.getAreaName();;
        this.address = dto.getAddress();
        this.pay = dto.getPay();
        this.tel = dto.getTel();
        this.description = dto.getDescription();
        this.user = user;
    }
}
