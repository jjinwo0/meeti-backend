package yjhb.meeti.user.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import yjhb.meeti.api.login.validate.Validator;
import yjhb.meeti.global.util.AuthorizationHeaderUtils;
import yjhb.meeti.api.login.dto.LoginDTO;
import yjhb.meeti.user.dto.UserDTO;
import yjhb.meeti.user.entity.User;
import yjhb.meeti.user.repository.UserRepository;
import yjhb.meeti.user.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final Validator validator;

    @PostMapping("/join")
    public ResponseEntity<User> join(@RequestBody UserDTO dto){
        Long joinId = userService.join(dto);

        return ResponseEntity
                .ok()
                .body(userRepository.findById(joinId).get());
    }
    @PostMapping("/update/{id}")
    public ResponseEntity<User> update(@RequestBody UserDTO dto, @PathVariable(name = "id") Long id){

        User findUser = userRepository.findById(id)
                .orElseThrow(IllegalStateException::new);

        User updateUser = findUser.update(dto);

        return ResponseEntity
                .ok()
                .body(updateUser);
    }
}
