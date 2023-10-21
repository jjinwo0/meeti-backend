package yjhb.meeti.dto.office;

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
    private String telNum;

}
