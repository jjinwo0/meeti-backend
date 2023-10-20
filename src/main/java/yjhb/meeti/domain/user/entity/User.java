package yjhb.meeti.domain.user.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import yjhb.meeti.domain.approval.entity.Approval;
import yjhb.meeti.domain.calender.Calender;
import yjhb.meeti.domain.chat.ChatUser;
import yjhb.meeti.domain.common.BaseTimeEntity;
import yjhb.meeti.domain.meeting.Meeting;
import yjhb.meeti.domain.office.Office;
import yjhb.meeti.domain.reservation.Reservation;
import yjhb.meeti.domain.user.constant.Role;
import yjhb.meeti.domain.user.constant.UserType;
import yjhb.meeti.global.jwt.dto.JwtTokenDto;
import yjhb.meeti.global.util.DateTimeUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private UserType userType;

    @Column(unique = true, length = 50, nullable = false)
    private String email;

    @Column(length = 200)
    private String password;

    @Column(nullable = false, length = 20)
    private String username;

    @Column(length = 200)
    private String profile; // 프로필 사진 주소 저장 컬럼

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(length = 250)
    private String refreshToken;

    private LocalDateTime tokenExpirationTime;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Friend> friends = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Calender> calenders = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Office> offices = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Meeting> meetings = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Approval> approvals = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ChatUser> chatUsers = new ArrayList<>();

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

    public User update(String username, String profile){
        this.username = username;
        this.profile = profile;

        return this;
    }

    public void updateToken(JwtTokenDto jwtTokenDto) {
        this.refreshToken = jwtTokenDto.getRefreshToken();
        this.tokenExpirationTime = DateTimeUtils.convertToLocalDateTime(jwtTokenDto.getRefreshTokenExpireTime());
    }

    public void expireToken(LocalDateTime now) {
        this.tokenExpirationTime = now;
    }
}
