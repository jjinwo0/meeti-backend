package yjhb.meeti.jwt.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class JwtUtils {

    //json web token 생성
    public static String createJwt(String username, String secretKey, Long expiredMs){
        Claims claims = Jwts.claims();
        claims.put("username", username);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+expiredMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public static String getUsername(String token, String secretKey){

        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("username", String.class);
    }

    public static boolean isExpired(String token, String secretKey){

        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
    }

//    public String resolveToken(HttpServletRequest request){
//        return request.getHeader("Authorization");
//    }
}
