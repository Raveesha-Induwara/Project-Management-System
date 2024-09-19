package com.raveesha.service;

import com.raveesha.config.JwtProvider;
import com.raveesha.request.LoginRequestDto;
import com.raveesha.request.SignupRequestDto;
import com.raveesha.model.User;
import com.raveesha.repo.UserRepository;
import com.raveesha.response.AuthResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private SubscriptionService subscriptionService;
    
    public ResponseEntity<AuthResponseDto> signupUserHandler(SignupRequestDto request) throws Exception {
        User isUserExist = userRepo.findByEmail(request.getEmail());
        
        if(isUserExist != null) {
            throw new Exception("Email already registered with another account");
        }
        
        User newUser = new User();
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setEmail(request.getEmail());
        newUser.setFullName(request.getFullName());
        userRepo.save(newUser);
        
        subscriptionService.createSubscription(newUser);
        
        Authentication authentication = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtProvider.generateToken(authentication);
        
        AuthResponseDto res = new AuthResponseDto();
        res.setJwt(jwtToken);
        res.setMessage("Signup success");
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
    
    public ResponseEntity<AuthResponseDto> loginUserHandler(LoginRequestDto request) {
        String username = request.getEmail();
        String password = request.getPassword();
        
        Authentication authentication = authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtToken = jwtProvider.generateToken(authentication);
        
        AuthResponseDto res = new AuthResponseDto();
        res.setJwt(jwtToken);
        res.setMessage("Login success");
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
    
    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        if(userDetails == null) {
            throw new BadCredentialsException("Invalid username");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
