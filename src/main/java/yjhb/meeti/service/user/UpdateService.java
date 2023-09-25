package yjhb.meeti.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import yjhb.meeti.domain.user.entity.User;
import yjhb.meeti.service.file.S3Service;
import yjhb.meeti.service.user.UserService;

import java.io.IOException;

@Service
@Transactional
@RequiredArgsConstructor
public class UpdateService {

    private final UserService userService;
    private final S3Service s3Service;

    public Long updateUser(Long userId, String username, MultipartFile image) throws IOException {
        User findUser = userService.findUserByUserId(userId);

        if (!image.isEmpty()){

            String profileImages = s3Service.upload(image, "profileImages");
            findUser.update(username, profileImages);
        }else findUser.update(username, null);

        return findUser.getId();
    }
}
