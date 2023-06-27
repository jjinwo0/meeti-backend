package yjhb.meeti.global.resolver.memberinfo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER) // 컨트롤러 클래스 메서드
@Retention(RetentionPolicy.RUNTIME)
public @interface MemberInfo {
}
