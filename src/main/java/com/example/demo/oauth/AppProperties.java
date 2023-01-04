package com.example.demo.oauth;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
/*
    바인딩하는데 유효한 Value값
    @ConfigurationProperties는 Springboot에서 properties 파일에 정의된 프로퍼티 중 주어진 prefix를 가지는
    프로퍼티들을 POJO에 매핑하여 Bean으로 만들 수 있게 해주는 어노테이션이다.
    POJO란? -> 객체 지향적인 원리에 충실하면서 환경과 기술에 종속되지 않고 필요에 따라 재활용 될 수 있는 방식으로 설계된 오브젝트이다.
    POJO Class?
    ConfigurationProperties는 resource에 있는 application.properties에서 설정한 접두사 app을 가져올라고 사용하는것
    maven에서는 설정을 하지 않아서 어노테이션을 사용안해도 된다.

 */
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private final Auth auth = new Auth();
    private final OAuth2 oAuth2 = new OAuth2();

    public static class Auth {
        private String tokenSecret;
        private long tokenExpirationMsec;

        public String getTokenSecret() {
            return tokenSecret;
        }

        public void setTokenSecret(String tokenSecret) {
            this.tokenSecret = tokenSecret;
        }

        public long getTokenExpirationMsec() {
            return tokenExpirationMsec;
        }

        public void setTokenExpirationMsec(long tokenExpirationMsec) {
            this.tokenExpirationMsec = tokenExpirationMsec;
        }
    }

    public static final class OAuth2 {
        private List<String> authorizedRedirectUris = new ArrayList<>();
        public List<String> getAuthorizedRedirectUris() {
            return authorizedRedirectUris;
        }
        public OAuth2 authorizedRedirectUris(List<String> authorizedRedirectUris) {
            this.authorizedRedirectUris = authorizedRedirectUris;
            return this;
        }
    }

    public Auth getAuth() {
        return auth;
    }

    public OAuth2 getoAuth2() {
        return oAuth2;
    }
}
