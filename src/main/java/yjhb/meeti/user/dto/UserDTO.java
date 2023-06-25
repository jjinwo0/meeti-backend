package yjhb.meeti.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter @Setter
@NoArgsConstructor
public class UserDTO {

    @NotEmpty(message = "필수 입력 항목입니다.")
    private String username;
    @NotEmpty(message = "필수 입력 항목입니다.")
    private String password;
    @Email
    @NotEmpty(message = "필수 입력 항목입니다.")
    private String email;
}
