package com.spring.tiketsys.service;

import com.spring.tiketsys.dto.entity.User;
import com.spring.tiketsys.repository.UserRepository;
import com.spring.tiketsys.security.exceptions.TicketException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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
    public void deleteUser(User user){
        userRepository.delete(user);
    }

    @Transactional
    public boolean usernameExists(String username){
        return (userRepository.getUserByUserName(username)!=null);
    }

    @Transactional
    public User getUser(String username) throws TicketException{
        try{
            return userRepository.getUserByUserName(username);
        }catch (Exception ex){
            throw new TicketException("no se encontro el usuario ");
        }
    }

    public List<Map<String, Object>> getAllSecure(String userException) { return userRepository.getAllUsersSecure(userException); }
}
