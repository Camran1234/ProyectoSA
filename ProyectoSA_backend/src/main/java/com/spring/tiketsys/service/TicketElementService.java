package com.spring.tiketsys.service;

import com.spring.tiketsys.repository.TicketElementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketElementService {
    @Autowired
    private TicketElementRepository ticketElementRepository;
}
