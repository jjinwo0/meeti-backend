package yjhb.meeti.api.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yjhb.meeti.global.jwt.service.TokenManager;
import yjhb.meeti.service.user.UserService;

import javax.servlet.http.HttpServletRequest;

@Tag(name = "Delete User", description = "유저 정보 삭제 API")
@RestController
@RequestMapping("/meeti/user")
@RequiredArgsConstructor
public class DeleteController {

    private final UserService userService;
    private final TokenManager tokenManager;

    @Tag(name = "Delete User")
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Boolean> delete(@PathVariable("userId") Long userId,
                                          HttpServletRequest request){

        String authorization = request.getHeader("Authorization");
        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        userService.deleteUser(userId);

        return ResponseEntity.ok(true);
    }
}
