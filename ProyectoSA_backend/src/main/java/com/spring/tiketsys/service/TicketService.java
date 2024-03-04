package com.spring.tiketsys.service;

import com.spring.tiketsys.dto.entity.Ticket;
import com.spring.tiketsys.dto.model.TicketDTO;
import com.spring.tiketsys.repository.TicketElementRepository;
import com.spring.tiketsys.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TicketElementRepository ticketElementRepository;

    @Transactional
    public Ticket createTicket(Ticket ticket){
        return ticketRepository.saveAndFlush(ticket);
    }

    @Transactional
    public List<Map<String, Object>> getTicketsEmail(String email){
        List<Map<String,Object>> tickets = ticketRepository.getTicketsByUsername(email);
        return tickets;
    }

    @Transactional
    public List<String> getTicketsElements(int ticketNumber){
        return  ticketElementRepository.getElements(ticketNumber);
    }

    public Ticket getReferenceById(int id){
        return ticketRepository.getReferenceById(id);
    }
}
