package com.spring.tiketsys.service;

import com.spring.tiketsys.dto.entity.User;
import com.spring.tiketsys.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    /**
     * Create user
     */
    @Transactional
    public Object registerUser(User user){
        return userRepository.saveAndFlush(user);
    }

    @Transactional
    public boolean usernameExists(String username){
        return (userRepository.getUserByUserName(username)!=null);
    }

    @Transactional
    public User getUser(String username){
        return userRepository.getUserByUserName(username);
    }
}
