package com.example.demo.oauth;


import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//OncePerRequestFilter란? 어느 서블릿 컨테이너에서나 요청 당 한 번의 실행을 보장하는것을 목표로
/*
 * 여기서 서블릿이란? 서블릿은 웹 프로그래밍에서 클라이언트 요청을 처리하고
 * 처리 결과를 클라이언트에 전송하는 기술
 * 서블릿의 특징 : Java Thread를 통해 작동
 * MVC패턴중 Controller로 이용된다.
 */
public  abstract class TokenAuthenticationFilter  extends OncePerRequestFilter {

    private TokenProvider tokenProvider;
    private AppProperties appProperties;


    public TokenAuthenticationFilter(TokenProvider tokenProvider, AppProperties appProperties) {
        this.tokenProvider = tokenProvider;
        this.appProperties = appProperties;
    }

    protected abstract void setUser(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, String idUser);


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);

            String idUser =null;
            if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                System.out.println("Token Validate ok:" + jwt);
                idUser = tokenProvider.getUserIdFromToken(jwt);
                System.out.println("idUser :" + idUser);
                setUser(request, response, filterChain, idUser);
            } else {
                String msg = "";
            }
        }catch (Exception e) {

        }
        //filterChain 요청과 응답에 대한 정보들을 변경할 수 있게 개발자들에게 제공한느 서블릿 컨테이너
        filterChain.doFilter(request, response);
    }
    //getJwtFromRequest에서 토큰이 생성된다.
    private String getJwtFromRequest(HttpServletRequest request) {
        //지정된 요청의 헤더 값을 반환한다.
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length());
        }
        return null;
    }
}
