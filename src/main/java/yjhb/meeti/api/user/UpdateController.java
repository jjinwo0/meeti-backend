package yjhb.meeti.api.user;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.service.user.UpdateService;
import yjhb.meeti.global.jwt.service.TokenManager;
import yjhb.meeti.global.util.AuthorizationHeaderUtils;
import yjhb.meeti.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Tag(name = "Update", description = "유저 정보 update API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/meeti/user")
public class UpdateController {

    private final UpdateService updateService;
    private final UserService userService;
    private final TokenManager tokenManager;

    @Tag(name = "Update User")
    @PostMapping(value = "/update/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Boolean> updateUser(@PathVariable("userId") Long userId,
                                              @RequestPart(value = "username")  String username,
                                              @RequestPart(value = "image")  MultipartFile image,
                                              HttpServletRequest httpServletRequest) throws IOException {

        String authorization = httpServletRequest.getHeader("Authorization");
        AuthorizationHeaderUtils.validateAuthorization(authorization);

        String accessToken = authorization.split(" ")[1];

        tokenManager.validateToken(accessToken);

        User findUser = userService.findUserByUserId(userId);

        updateService.updateUser(findUser.getId(), username, image);

        return ResponseEntity.ok(true);
    }
}
