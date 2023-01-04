package com.example.demo.oauth;


import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import java.util.Date;

@Service
public class TokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);


    private AppProperties appProperties;
    //JWT Configuration
    //JWT Token의 유효기간 을 설정하기
    private int tokenExpirationMsec = 864000000;
    //JWT  Token을 hash할 때 사용하는 SecretKey
    //hash란? ->임의의 크기를
    private String tokenSecret = "926D96C90030DD58429D2751AC1BDBBC";

    public TokenProvider(AppProperties appProperties) {
        this.appProperties = appProperties;
    }


    public String createToken(Authentication authentication) {
        //여기서 UserDetails란?
		/*
		 	SpringSecurity에서 사용자의 정보를 담는 인터페이스
		 	getPrincipal -> 인증되는 주체 ID, 사용자 이름과 암호가 있는 인증 요청의 경우 사용자 이름이 됨
		 */
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        Date now = new Date();
        //하드코딩으로 지정한 유효기간을 Date화
        Date expiryDate = new Date(now.getTime() + tokenExpirationMsec);
        //JWT 인터페이스의 인스턴스를 만드는데 유용한 팩토리 클래스
        //이팩토리 클래스를 사용하면 코드를 구현 클래스에 단단히 결합함
        //JWT를 빌드하고 JWT Compact Serializastionrules에 따라 압축된 URL 안전 문자열로 직렬화
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, tokenSecret)

                .compact();

    }
    //Claims는 궁극적인 JSON맵, 모든 값을 추가할 수 있음
    //사용자에대한 프로퍼티와  토큰 자체도
    private Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                //검색된 JWS디지털 서명을 확인하는데 사용되는 서명 키
                .setSigningKey(tokenSecret)
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUserIdFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    //토큰 확인
    public boolean validateToken(String authToken) {
        try {
            //Jwts 팩토리에서 반환된 토큰확인
            Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty.");
        }
        return false;
    }
}
