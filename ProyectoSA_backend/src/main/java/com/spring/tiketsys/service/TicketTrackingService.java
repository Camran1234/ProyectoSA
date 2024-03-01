package com.spring.tiketsys.service;

import com.spring.tiketsys.repository.TicketTrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketTrackingService {
    @Autowired
    private TicketTrackingRepository ticketTrackingRepository;
}
