package com.spring.tiketsys.service;

import com.spring.tiketsys.repository.TicketPriorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketPriorityService {
    @Autowired
    private TicketPriorityRepository ticketPriorityRepository;
}
