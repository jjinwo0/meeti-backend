package yjhb.meeti.api.search.office.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter @Builder
@NoArgsConstructor @AllArgsConstructor
public class OfficeResponseDto {
    private String placeName;
    private int pay;
    private String description;
    private String address;
}
