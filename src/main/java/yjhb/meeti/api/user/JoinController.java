package yjhb.meeti.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yjhb.meeti.dto.user.UserJoinDTO;
import yjhb.meeti.service.user.EmailService;
import yjhb.meeti.service.user.JoinService;
import yjhb.meeti.domain.user.constant.Role;
import yjhb.meeti.domain.user.constant.UserType;
import yjhb.meeti.domain.user.entity.User;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meeti/user")
public class JoinController {

    private final JoinService joinService;
    private final EmailService emailService;

    @PostMapping("/join")
    public ResponseEntity<Boolean> join(@RequestBody UserJoinDTO userJoinDTO){

        User createUser = User.builder()
                .userType(UserType.COMMON)
                .email(userJoinDTO.getEmail())
                .password(userJoinDTO.getPassword())
                .username(userJoinDTO.getUsername())
                .role(Role.COMMON)
                .build();

        joinService.join(createUser);

        return ResponseEntity.ok(true);
    }

    @PostMapping("/join/office")
    public ResponseEntity<Boolean> joinOffice(@RequestBody UserJoinDTO userJoinDTO){

        User createUser = User.builder()
                .userType(UserType.COMMON)
                .email(userJoinDTO.getEmail())
                .password(userJoinDTO.getPassword())
                .username(userJoinDTO.getUsername())
                .role(Role.COMMON_OFFICE)
                .build();

        joinService.join(createUser);

        return ResponseEntity.ok(true);
    }
}
