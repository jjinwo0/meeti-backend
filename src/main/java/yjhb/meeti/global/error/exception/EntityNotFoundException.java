package yjhb.meeti.global.error.exception;

import yjhb.meeti.global.error.ErrorCode;

public class EntityNotFoundException extends BusinessException{

    public EntityNotFoundException(ErrorCode errorCode){
        super(errorCode);
    }
}

