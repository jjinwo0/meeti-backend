package yjhb.meeti.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import yjhb.meeti.dto.UserDTO;
import yjhb.meeti.entity.User;
import yjhb.meeti.repository.UserRepository;
import yjhb.meeti.service.UserService;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
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
}
