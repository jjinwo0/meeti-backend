package yjhb.meeti.domain.chat;

public enum MessageType {

    ENTER, MESSAGE;

    public static MessageType from(String type) {
        return MessageType.valueOf(type);
    }
}
