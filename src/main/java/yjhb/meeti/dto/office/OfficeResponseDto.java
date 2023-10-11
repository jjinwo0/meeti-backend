package yjhb.meeti.dto.office;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yjhb.meeti.domain.office.Office;

@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class OfficeResponseDto {
    private Long id;
    private String placeName;
    private String pay;
    private String description;
    private String address;
    private String addressDetail;
    private String telNum;
    private String image;
    private boolean status;

    public static OfficeResponseDto from(Office office){

        return OfficeResponseDto.builder()
                .id(office.getId())
                .placeName(office.getPlaceName())
                .pay(office.getPay())
                .description(office.getDescription())
                .address(office.getAddress())
                .addressDetail(office.getDetailAddress())
                .telNum(office.getTelNum())
                .image(office.getImage())
                .status(office.isStatus())
                .build();
    }
}
