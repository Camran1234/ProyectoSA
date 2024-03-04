package com.spring.tiketsys.service;

import com.spring.tiketsys.dto.entity.State_of_Ticket;
import com.spring.tiketsys.dto.entity.Ticket;
import com.spring.tiketsys.dto.entity.TicketTracking;
import com.spring.tiketsys.repository.TicketTrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TicketTrackingService {
    @Autowired
    private TicketTrackingRepository ticketTrackingRepository;

    public TicketTracking findById(int id){
        return  ticketTrackingRepository.findById(id).get();
    }


    public TicketTracking saveTicket(TicketTracking ticketTracking){
        return ticketTrackingRepository.saveAndFlush(ticketTracking);
    }

    public TicketTracking createTrack(Ticket ticket, State_of_Ticket stateOfTicket){

        TicketTracking ticketTracking = new TicketTracking(
                ticket.getTicketNumber(),
                stateOfTicket,
                new Date(),
                new Date(),
                null,
                false,
                ticket
        );
        return ticketTrackingRepository.saveAndFlush(ticketTracking);
    }

}
