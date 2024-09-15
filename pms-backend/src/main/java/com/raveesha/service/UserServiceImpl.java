package com.raveesha.service;

import com.raveesha.config.JwtProvider;
import com.raveesha.model.User;
import com.raveesha.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private JwtProvider jwtProvider;
    
    @Override
    public User findUserProfileByJwt(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromToken(jwt);
        return findByEmail(email);
    }
    
    @Override
    public User findByEmail(String email) throws Exception {
        User user = userRepo.findByEmail(email);
        if(user == null) {
            throw new Exception("User not found");
        }
        
        return user;
    }
    
    @Override
    public User findById(Long userId) throws Exception {
        Optional<User> optionalUser = userRepo.findById(userId);
        if(optionalUser.isEmpty()) {
            throw new Exception("User not found");
        }
        
        return optionalUser.get();
    }
    
    @Override
    public User updateUsersProjectSize(User user, int number) throws Exception {
        user.setProjectSize(user.getProjectSize() + number);
        return userRepo.save(user);
    }
}
