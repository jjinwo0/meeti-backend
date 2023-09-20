package yjhb.meeti.dto.user;

import lombok.Data;

@Data
public class EmailDto {

    private String email;

    @Data
    public static class Valid{

        private String code;
    }
}
