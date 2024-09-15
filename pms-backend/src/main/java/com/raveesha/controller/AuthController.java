package com.raveesha.controller;

import com.raveesha.request.LoginRequestDto;
import com.raveesha.request.SignupRequestDto;
import com.raveesha.model.User;
import com.raveesha.response.AuthResponseDto;
import com.raveesha.service.AuthService;
import com.raveesha.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    
    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDto> signupUserHandler(@RequestBody SignupRequestDto request) throws Exception {
        return authService.signupUserHandler(request);
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> loginUserHandler(@RequestBody LoginRequestDto request) throws Exception {
        return authService.loginUserHandler(request);
    }
    
}
