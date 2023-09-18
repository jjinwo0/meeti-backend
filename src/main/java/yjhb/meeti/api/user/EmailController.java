package yjhb.meeti.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import yjhb.meeti.dto.user.EmailDto;
import yjhb.meeti.service.user.EmailService;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/valid")
    public ResponseEntity<String> validEmail(@RequestBody EmailDto dto){

        int number = emailService.sendMail(dto.getEmail());

        String num = "" + number;

        return ResponseEntity.ok(num);
    }
}
