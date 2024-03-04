package com.spring.tiketsys.service;

import com.spring.tiketsys.dto.entity.Ticket;
import com.spring.tiketsys.dto.entity.TicketElement;
import com.spring.tiketsys.repository.TicketElementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TicketElementService {
    @Autowired
    private TicketElementRepository ticketElementRepository;

    @Transactional
    public TicketElement saveElement(Ticket ticket, String url){
        int id = generateNextId(ticket.getTicketNumber());
        System.out.println("TicketElement Id:"+id+", ticketNumber"+ticket.getTicketNumber());
        return ticketElementRepository.saveAndFlush(new TicketElement(
                id,
                ticket.getTicketNumber(),
                url
        ));
    }


    @Transactional
    public int generateNextId(int ticketNumber) {
        // Consulta la base de datos para encontrar el máximo valor actual de idHistory para el ticketNumber dado
        Optional<Integer> maxId = ticketElementRepository.findMaxIdForTicketElement(ticketNumber);
        int nextId = maxId.orElse(0) + 1;
        return nextId;
    }
}
