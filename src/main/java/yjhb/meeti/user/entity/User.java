package yjhb.meeti.user.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yjhb.meeti.calender.entity.Calender;
import yjhb.meeti.global.jwt.dto.JwtTokenDto;
import yjhb.meeti.global.util.DateTimeUtils;
import yjhb.meeti.reservation.entity.Reservation;
import yjhb.meeti.user.constant.Role;
import yjhb.meeti.user.dto.UserDTO;

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
    private Role role;
    @OneToMany(mappedBy = "user")
    List<Calender> calenders;
    @OneToMany(mappedBy = "user")
    List<Reservation> reservations;
    @Column(length = 250)
    private String refreshToken;
    private LocalDateTime tokenExpirationTime;

    @Builder
    public User(UserDTO dto) {
        this.username = dto.getUsername();
        this.password = dto.getPassword();
        this.email = dto.getEmail();
    }

    public User update(UserDTO dto){
        this.username = dto.getUsername();
        this.password = dto.getPassword();
        this.email = dto.getEmail();

        return this;
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
