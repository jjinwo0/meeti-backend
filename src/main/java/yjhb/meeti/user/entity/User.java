package yjhb.meeti.user.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import yjhb.meeti.global.jwt.dto.JwtTokenDto;
import yjhb.meeti.global.util.DateTimeUtils;
import yjhb.meeti.user.constant.UserType;
import yjhb.meeti.user.constant.Role;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", updatable = false)
    private Long id;
    @NotEmpty
    private String username;
    @NotEmpty
    private String password;
    @NotEmpty @Email
    private String email;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    @ColumnDefault("COMMON")
    private Role role;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    @ColumnDefault("COMMON")
    private UserType userType;
    @Column(length = 200)
    private String profile; // 프로필 사진 주소 저장 컬럼
    @Column(length = 250)
    private String refreshToken;
    private LocalDateTime tokenExpirationTime;

    @Builder
    public User(UserType userType, String email, String password, String username,
                String profile, Role role){
        this.userType = userType;
        this.email = email;
        this.password = password;
        this.username = username;
        this.profile = profile;
        this.role = role;
    }

    public void updateRefreshToken(JwtTokenDto jwtTokenDto){
        this.refreshToken = jwtTokenDto.getRefreshToken();
        this.tokenExpirationTime = DateTimeUtils.convertToLocalDateTime(jwtTokenDto.getRefreshTokenExpireTime());
    }

    // 토큰 만료를 위한 메서드
    public void expireRefreshToken(LocalDateTime now){
        this.tokenExpirationTime = now;
    }
}
