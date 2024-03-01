package com.spring.tiketsys.service;

import com.spring.tiketsys.repository.History_of_CommunicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class History_of_CommunicationService {
    @Autowired
    private History_of_CommunicationRepository historyOfCommunicationRepository;

}
