package yjhb.meeti.api.join.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yjhb.meeti.api.join.dto.UserJoinDTO;
import yjhb.meeti.api.join.service.JoinService;
import yjhb.meeti.domain.user.constant.Role;
import yjhb.meeti.domain.user.constant.UserType;
import yjhb.meeti.domain.user.entity.User;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class JoinController {

    private final JoinService joinService;

    @PostMapping("/join")
    public ResponseEntity<String> join(@RequestBody UserJoinDTO userJoinDTO){

        User createUser = User.builder()
                .userType(UserType.COMMON)
                .email(userJoinDTO.getEmail())
                .password(userJoinDTO.getPassword())
                .username(userJoinDTO.getUsername())
                .profile(userJoinDTO.getProfile())
                .role(Role.COMMON)
                .build();

        joinService.join(createUser);

        return ResponseEntity.ok("Join Success");
    }
}
