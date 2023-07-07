package yjhb.meeti.api.user.oauth.validate;

import org.springframework.stereotype.Service;
import yjhb.meeti.domain.user.constant.UserType;
import yjhb.meeti.global.error.ErrorCode;
import yjhb.meeti.global.error.exception.BusinessException;

@Service
public class OAuthValidator {

    public void validateUserType(String userType){
        if (!UserType.isUserType(userType))
            throw new BusinessException(ErrorCode.INVALID_MEMBER_TYPE);
    }
}
