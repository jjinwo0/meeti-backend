package yjhb.meeti.domain.office.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Office {

    @Id @GeneratedValue
    private Long id;
    private String placeName;
    private int pay;
    private String description;
    private String address;
    private String detailAddress;
    private String image;
    private String telNum;

    @Builder
    public Office(String image, String telNum, int pay, String placeName, String description, String address, String detailAddress) {
        this.image = image;
        this.telNum = telNum;
        this.pay = pay;
        this.placeName = placeName;
        this.description = description;
        this.address = address;
        this.detailAddress = detailAddress;
    }
}
