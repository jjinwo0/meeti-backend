package yjhb.meeti.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import yjhb.meeti.dto.AuthRequestDTO;
import yjhb.meeti.dto.EmailRequestDTO;
import yjhb.meeti.dto.LoginDTO;
import yjhb.meeti.dto.UserDTO;
import yjhb.meeti.entity.User;
import yjhb.meeti.repository.UserRepository;
import yjhb.meeti.service.UserService;
import yjhb.meeti.service.mail.MailService;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Base64;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final MailService mailService;

    @PostMapping("/join")
    public ResponseEntity<User> join(@RequestBody UserDTO dto){
        userService.join(dto);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/join/email")
    public ResponseEntity<Void> authEmail(@RequestBody @Valid EmailRequestDTO dto, @RequestBody @Valid AuthRequestDTO auth) throws MessagingException {
        String authCode = mailService.sendEmail(dto.getEmail());

        if (authCode.equals(auth.getCode()))
            return ResponseEntity.ok().build();
         else return ResponseEntity.notFound().build();
    }

    @PostMapping("/update/{id}")
    public String update(@RequestBody UserDTO dto, @PathVariable(name = "id") Long id){

        User findUser = userRepository.findById(id)
                .orElseThrow(IllegalStateException::new);

        findUser.update(dto);

        return "redirect:/";
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO dto, HttpSession session){

        String loginToken = userService.login(dto);
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String username = String.valueOf(decoder.decode(loginToken)[1]);
        User loginUser = userRepository.findByUsername(username).get();

        session.setAttribute("loginUser", loginUser);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.login(dto));
    }
}
