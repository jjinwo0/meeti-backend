package yjhb.meeti.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import yjhb.meeti.dto.LoginDTO;
import yjhb.meeti.dto.UserDTO;
import yjhb.meeti.entity.User;
import yjhb.meeti.repository.UserRepository;
import yjhb.meeti.service.UserService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    @PostMapping("/join")
    public String join(@RequestBody UserDTO dto){

        userService.join(dto);

        return "redirect:/";
    }

    @PostMapping("/update/{id}")
    public String update(@RequestBody UserDTO dto, @PathVariable(name = "id") Long id){

        User findUser = userRepository.findById(id)
                .orElseThrow(IllegalStateException::new);

        findUser.update(dto);

        return "redirect:/";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO dto){
        return ResponseEntity.ok().body(userService.login(dto));
    }
}
