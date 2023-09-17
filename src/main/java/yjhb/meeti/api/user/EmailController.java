package yjhb.meeti.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yjhb.meeti.service.user.EmailService;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/valid/{email:.+}")
    public String validEmail(@PathVariable("email") String email) throws Exception {

        String send = emailService.sendSimpleMessage(email);

        return send;
    }
}
