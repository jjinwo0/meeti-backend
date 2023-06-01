package yjhb.meeti.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class EmailRequestDTO {

    @Email
    @NotEmpty(message = "이메일을 입력해주세요.")
    private String email;
}
