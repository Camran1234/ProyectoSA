package com.spring.tiketsys.service;

import com.spring.tiketsys.dto.entity.State_of_Ticket;
import com.spring.tiketsys.repository.State_of_TicketRepository;
import com.spring.tiketsys.security.exceptions.TicketException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class State_of_TicketService {
    @Autowired
    private State_of_TicketRepository stateOfTicketRepository;


    public State_of_Ticket getByState(String state) throws TicketException{
        List<State_of_Ticket> list = stateOfTicketRepository.findAll();
        for(State_of_Ticket stateOfTicket: list){
            if(stateOfTicket.getState().equalsIgnoreCase(state)){
                return stateOfTicket;
            }
        }
        throw new TicketException("Estado de ticket no encontrado, proporcionado: "+state);
    }

    public State_of_Ticket getReferencedById(int id){
        return stateOfTicketRepository.getReferenceById(id);
    }
}
