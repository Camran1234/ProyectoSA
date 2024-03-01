package com.spring.tiketsys.service;

import com.spring.tiketsys.repository.State_of_TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class State_of_TicketService {
    @Autowired
    private State_of_TicketRepository stateOfTicketRepository;
}
