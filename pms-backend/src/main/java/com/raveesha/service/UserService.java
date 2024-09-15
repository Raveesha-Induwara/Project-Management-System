package com.raveesha.service;

import com.raveesha.model.User;
import org.springframework.stereotype.Service;

public interface UserService {
    User findUserProfileByJwt(String jwt) throws Exception;
    User findByEmail(String email) throws Exception;
    User findById(Long userId) throws Exception;
    User updateUsersProjectSize(User user, int number) throws Exception;
}
