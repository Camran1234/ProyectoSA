package com.spring.tiketsys.service;

import com.spring.tiketsys.dto.entity.TicketPriority;
import com.spring.tiketsys.repository.TicketPriorityRepository;
import com.spring.tiketsys.security.exceptions.TicketException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketPriorityService {
    @Autowired
    private TicketPriorityRepository ticketPriorityRepository;

    public TicketPriority getByPriority(String priority) throws  TicketException{
        List<TicketPriority> ticketPriorityList = ticketPriorityRepository.findAll();
        for(TicketPriority ticketPriority:ticketPriorityList){
            if(ticketPriority.getPriority().equalsIgnoreCase(priority)){
                return ticketPriority;
            }
        }
        throw new TicketException("No se encontro la prioridad del ticket, proporcionado: "+priority);
    }

    public TicketPriority findById(int id){
        return ticketPriorityRepository.findById(id).get();
    }
}
