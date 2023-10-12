package yjhb.meeti.domain.office;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yjhb.meeti.domain.reservation.Reservation;
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
    private String pay;
    private String description;
    private String address;
    private String detailAddress;
    private String image;
    private String telNum;
    private boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "office", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();

    @Builder
    public Office(User user, String image, String telNum, String pay, String placeName, String description, String address, String detailAddress, boolean status) {
        this.user = user;
        this.image = image;
        this.telNum = telNum;
        this.pay = pay;
        this.placeName = placeName;
        this.description = description;
        this.address = address;
        this.detailAddress = detailAddress;
        this.status = status;
        user.getOffices().add(this);
    }

    public void updateStatus(boolean status){
        this.status = status;
    }
}
