package yjhb.meeti.api.user.login.validate;

import org.springframework.stereotype.Service;
import yjhb.meeti.global.error.ErrorCode;
import yjhb.meeti.global.error.exception.BusinessException;
import yjhb.meeti.domain.user.constant.UserType;

@Service
public class Validator {

    public void validateMemberType(String memberType){
        if (!UserType.isUserType(memberType))
            throw new BusinessException(ErrorCode.INVALID_MEMBER_TYPE);
    }
}
