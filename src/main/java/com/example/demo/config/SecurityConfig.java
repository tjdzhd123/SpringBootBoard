package com.example.demo.config;


import com.example.demo.oauth.TokenAuthenticationFilter;
import com.example.demo.oauth.TokenProvider;
import com.example.demo.service.OAuth2UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity // Spring Security를 활성화 시켜줌
@EnableGlobalMethodSecurity(
        securedEnabled = true, //securedEnabled true를 하면 @secured가 활성화가 된다 .(좀더 편하고 직관적인 권한 부여가 가능하다.)
        jsr250Enabled = true, //jsr250Enabled -> @RolesAllowed 어노테이션을 사용하여 인가 처리를 하고 싶을 때 사용하는 옵션
        prePostEnabled = true //@PreAuthorize, @PostAuthorize 어노테이션을 상요하여 인가 처리를 하고 싶을 때 사용하는 옵션
)
@Configuration //해당 클래스를 Configuration으로 설정
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private OAuth2UserService oAuth2UserService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenProvider tokenProvider;

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        //authentication 이라는 인증 메소드를 제공하는 인터페이스이다.
        return super.authenticationManagerBean();
    }

    //AuthenticationManagerBuilder를 통해 인증 객체를 만들 수 있도록 제공한다.
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        //인증추가하기
        authenticationManagerBuilder.userDetailsService(oAuth2UserService);
    }

    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
        return new TokenAuthenticationFilter(tokenProvider, null) {
            private final Logger logger = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

            @Override
            protected void setUser(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain, String idUser) {
                System.out.println("------------------------");
                System.out.println(idUser);
                System.out.println("-------------------------");

                UserDetails userDetails = oAuth2UserService.loadUserByUsername(idUser);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        };
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                //스프링시큐리티 세션정책 / 사용자 이름"User"로 인증하고 "User"로 인증을 시도하면 첫 번째 세션이 강제로 종료됨
                .sessionManagement()
                //스프링시큐리티가 생성하지도않고 기존것을 사용하지도 않음
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //JWT 같은 토큰방식을 사용하는 설정
                .and()
                //사이트간의 요청 위조
                //웹 애플리케이션 취약점 중 하나로 사용자가 자신의 의지와 무관하게 공격자가 의도한 행동을 하여 특정 웹페이지를 보안에 취약하게 한다거나 수정,
                //삭제 등의 작업을 하게 만드는 공격방법을 의미한다
                //예를 들어 A라는 도메인에서, 인증된 사용자 H가 위조된 request를 포함한 link, email을 사용하였을 경우(클릭, 또는 사이트 방문만이라도) A 도메인에서는 이 사용자가 일반 유저인지
                //아굥ㅇ된 공격인지 구분할 수 가 없음
                .csrf()
                .disable()
                //폼로그인 사용안함
                .formLogin()
                .disable()
                //httpbasic 사용안함
                .httpBasic()
                .disable()
                //예외처리란 프로그램 실행 흐름상 오류가 발생했을 떄 그 오류를 대처하는 방법
                .exceptionHandling()
                //인증 처리 과정에서 예외가 발생한 경우 예외를 핸들링하는 인터페이스입니다.
                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .and()
                //authorizeRequests()는 시큐리티 처리에 HttpServletRequest를 이용한다는 것을 의미합니다.
                //요청 URL에 따라 접근 권한을 설정
                .authorizeRequests()
                //antMatchers -> 요청 URL 경로 패턴을 지정
                .antMatchers("/userApi/**").permitAll()
//            .antMatchers("/testApi/**").authenticated()
                .anyRequest()
                //인증된 유저만 접근을 허용
                .authenticated();

        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    }
}
