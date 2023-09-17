package yjhb.meeti.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yjhb.meeti.dto.user.UserJoinDTO;
import yjhb.meeti.service.user.JoinService;
import yjhb.meeti.domain.user.constant.Role;
import yjhb.meeti.domain.user.constant.UserType;
import yjhb.meeti.domain.user.entity.User;

@RestController
@RequiredArgsConstructor
@RequestMapping("/meeti/user")
public class JoinController {

    private final JoinService joinService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody UserJoinDTO userJoinDTO){

        User createUser = User.builder()
                .userType(UserType.COMMON)
                .email(userJoinDTO.getEmail())
                .password(userJoinDTO.getPassword())
                .username(userJoinDTO.getUsername())
                .role(Role.COMMON)
                .build();

        joinService.join(createUser);

        return ResponseEntity.ok("Join Success");
    }

    @PostMapping("/join/office")
    public ResponseEntity<String> joinOffice(@RequestBody UserJoinDTO userJoinDTO){

        User createUser = User.builder()
                .userType(UserType.COMMON)
                .email(userJoinDTO.getEmail())
                .password(userJoinDTO.getPassword())
                .username(userJoinDTO.getUsername())
                .role(Role.COMMON_OFFICE)
                .build();

        joinService.join(createUser);

        return ResponseEntity.ok("Join Success");
    }
}
