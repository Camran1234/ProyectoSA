package com.spring.tiketsys.service;

import com.spring.tiketsys.repository.TicketTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketTypeService {
    @Autowired
    private TicketTypeRepository ticketTypeRepository;
}
