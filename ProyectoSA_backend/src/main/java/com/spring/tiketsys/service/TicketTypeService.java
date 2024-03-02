package com.spring.tiketsys.service;

import com.spring.tiketsys.dto.entity.TicketType;
import com.spring.tiketsys.dto.entity.UserType;
import com.spring.tiketsys.repository.TicketTypeRepository;
import com.spring.tiketsys.security.exceptions.TicketException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TicketTypeService {
    @Autowired
    private TicketTypeRepository ticketTypeRepository;

    @Transactional
    public TicketType getByType(String type) throws TicketException{
        List<TicketType> list = ticketTypeRepository.findAll();
        for(TicketType ticketType: list){
            if(ticketType.getType().equalsIgnoreCase(type)){
                return ticketType;
            }
        }
        throw new TicketException("Tipo de ticket no detectado ["+type+"]");
    }

    @Transactional
    public TicketType getReferenceById(int id){
        return ticketTypeRepository.getReferenceById(id);
    }


}
