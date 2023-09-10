package yjhb.meeti.domain.office.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yjhb.meeti.domain.reservation.entity.Reservation;
import yjhb.meeti.domain.user.entity.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Office {

    @Id @GeneratedValue
    @Column(name = "office_id")
    private Long id;
    private String placeName;
    private int pay;
    private String description;
    private String address;
    private String detailAddress;
    private String image;
    private String telNum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "office", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();

    @Builder
    public Office(User user, String image, String telNum, int pay, String placeName, String description, String address, String detailAddress) {
        this.user = user;
        this.image = image;
        this.telNum = telNum;
        this.pay = pay;
        this.placeName = placeName;
        this.description = description;
        this.address = address;
        this.detailAddress = detailAddress;
        user.getOffices().add(this);
    }
}
