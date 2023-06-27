package yjhb.meeti.domain.user.constant;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum UserType {
    COMMON, KAKAO;

    public static UserType from(String type){
        return UserType.valueOf(type.toUpperCase());
    }
    public static boolean isMemberType(String type){
        List<UserType> memberTypes = Arrays.stream(UserType.values())
                .filter(memberType -> memberType.name().equals(type))
                .collect(Collectors.toList());

        return memberTypes.size() != 0;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
