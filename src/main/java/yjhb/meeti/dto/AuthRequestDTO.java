package yjhb.meeti.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AuthRequestDTO {

    @NotEmpty
    private String code;
}
