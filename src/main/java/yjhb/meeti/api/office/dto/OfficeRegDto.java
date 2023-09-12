package yjhb.meeti.api.office.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class OfficeRegDto {

    private String placeName;
    private String pay;
    private String description;
    private String address;
    private String detailAddress;
    private String image;
    private String telNum;

}
