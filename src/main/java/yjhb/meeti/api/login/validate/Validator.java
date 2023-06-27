package yjhb.meeti.api.login.validate;

import org.springframework.stereotype.Service;
import yjhb.meeti.global.error.ErrorCode;
import yjhb.meeti.global.error.exception.BusinessException;
import yjhb.meeti.user.constant.UserType;

@Service
public class Validator {

    public void validateMemberType(String memberType){
        if (!UserType.isMemberType(memberType))
            throw new BusinessException(ErrorCode.INVALID_MEMBER_TYPE);
    }
}
