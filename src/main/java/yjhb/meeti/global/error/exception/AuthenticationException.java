package yjhb.meeti.global.error.exception;

import yjhb.meeti.global.error.ErrorCode;

public class AuthenticationException extends BusinessException{

    public AuthenticationException(ErrorCode errorCode){
        super(errorCode);
    }
}
