package com.spring.tiketsys.service;

import com.spring.tiketsys.repository.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserTypeService {
    @Autowired
    private UserTypeRepository userTypeRepository;
}
