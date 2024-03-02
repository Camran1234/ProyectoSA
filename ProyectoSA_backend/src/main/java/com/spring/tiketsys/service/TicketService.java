package com.spring.tiketsys.service;

import com.spring.tiketsys.dto.entity.Ticket;
import com.spring.tiketsys.dto.model.TicketDTO;
import com.spring.tiketsys.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;

    @Transactional
    public void createTicket(Ticket ticket){
        ticketRepository.saveAndFlush(ticket);
    }

    @Transactional
    public List<TicketDTO> getTicketsEmail(String email){
        return ticketRepository.getTicketsByUsername(email);
    }

    public Ticket getReferenceById(int id){
        return ticketRepository.getReferenceById(id);
    }
}
