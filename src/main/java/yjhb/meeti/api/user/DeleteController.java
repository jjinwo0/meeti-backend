package yjhb.meeti.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yjhb.meeti.service.user.UserService;

@RestController
@RequestMapping("/meeti/user")
@RequiredArgsConstructor
public class DeleteController {

    private final UserService userService;

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Boolean> delete(@PathVariable("userId") Long userId){

        userService.deleteUser(userId);

        return ResponseEntity.ok(true);
    }
}
