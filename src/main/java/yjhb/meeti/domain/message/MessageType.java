package yjhb.meeti.domain.message;

import yjhb.meeti.domain.user.constant.Role;

public enum MessageType {

    ENTER, MESSAGE;

    public static MessageType from(String type) {
        return MessageType.valueOf(type);
    }
}
