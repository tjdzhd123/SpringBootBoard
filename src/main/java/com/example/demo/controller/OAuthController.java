package com.example.demo.controller;

import com.example.demo.model.OauthToken;
import kong.unirest.Unirest;
import org.apache.commons.codec.binary.Base64;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OAuthController {

    // 클라이언트가 구현해야하는 코드 - 발급 받은 코드로 토큰 발행
    @RequestMapping("/callback")
    public OauthToken.response code(@RequestParam String code){
    	System.out.println("1");
        String cridentials = "clientId:secretKey";
        // base 64로 인코딩 (basic auth 의 경우 base64로 인코딩 하여 보내야한다.)
        String encodingCredentials = new String(
                Base64.encodeBase64(cridentials.getBytes()));
        String requestCode = code;
        OauthToken.request.accessToken request = new OauthToken.request.accessToken(){{
            setCode(requestCode);
            setGrant_type("authorization_code");
            setRedirect_uri("http://localhost:8081/callback");
            System.out.println("2");
        }};
        // oauth 서버에 http 통신으로 토큰 발행   
        //Unirest는 java뿐만 아니라 다양한 언어를 지원하고 있는 HTTP 통신클라이언트 라이브러리
        //GET, POST, PATCH,  DELETE등 여러가지 요청 방식을 지원하고 있으며, 비동기 통신 지원가능하다
        OauthToken.response oauthToken = Unirest.post("http://localhost:8081/oauth/token")
                .header("Authorization","Basic "+encodingCredentials)
                .fields(request.getMapData())
                //요청을 실행하고 구성된 ObjectMapper에 의해T에 매핑된 본문과 함께 응답을 반환함
                .asObject(OauthToken.response.class).getBody();
        System.out.println("3");
        return oauthToken;
    }

}