package com.example.demo.controller;

import com.example.demo.manager.UserManager;
import com.example.demo.model.ResponseDTO;
import com.example.demo.model.User;
import com.example.demo.oauth.TokenProvider;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/userApi")
@Slf4j
public class UserController {
    @Autowired
    private UserManager userManager;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/loginForm")
    @ApiOperation(value = "로그인", notes = "아이디,패스워드 인증 후 토큰을 발급해줍니다.")
    public ResponseEntity<String> authenticateUser(HttpServletRequest request, User user) {
        System.out.println("===================USER : "+user.toString());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        user.getPassword()
                )
        );
        System.out.println("====================1 "+user.toString());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        System.out.println("====================2 "+user.toString());
        String token = tokenProvider.createToken(authentication);
        System.out.println("==================== "+token);

        //api는 공통적으로 response 보낼때 데이터구조를 동일하게? 규칙적으로 보냄
        //그럴때 dto를 만들어서 동일하게 셋팅해서 리턴
        //responseEntity는 헤더 바디에 값을 세팅해야 클라에서 받으니까 아래처럼 설정
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setRes(token);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return ResponseEntity.ok()
                .headers(headers)
                .body(token);
    }

    @GetMapping("/user/list")
    public ArrayList<HashMap<String,Object>> findAll() {
        return userManager.findAll();
    }

    //ID 조회
    @RequestMapping(value = "/user/{seq}", method = RequestMethod.GET)
    public ResponseEntity<?> user_get(@PathVariable("id") String id) {
        ResponseDTO responseDTO = new ResponseDTO();
        List<User> user = userManager.getUser(id);
        if(user.size() > 0) {
            responseDTO.setResultCode("S01");
            responseDTO.setRes(userManager.getUser(id));
        }else {
            responseDTO.setResultCode("S02");
        }
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    //등록
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public String user_create(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("birth") String birth) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        user.setBirth(birth);
        user.setRole("Role_USER");
        userManager.createUser(user);
        System.out.print(user.toString());
        return user.getUsername();
    }






}
