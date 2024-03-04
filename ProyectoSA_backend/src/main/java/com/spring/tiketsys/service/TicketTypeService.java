package com.spring.tiketsys.service;

import com.spring.tiketsys.dto.entity.TicketType;
import com.spring.tiketsys.dto.entity.UserType;
import com.spring.tiketsys.repository.TicketTypeRepository;
import com.spring.tiketsys.security.exceptions.TicketException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    public TicketType findById(int id){
        Optional<TicketType> optionalTicketType = ticketTypeRepository.findById(id);
        if (optionalTicketType.isPresent()) {
            return optionalTicketType.get();
        } else {
            // Manejar el caso en que no se encuentre ninguna entidad con el ID especificado
            // Puedes lanzar una excepción, devolver null u otra acción dependiendo de tus necesidades
            throw new EntityNotFoundException("TicketType with id " + id + " not found");
        }
    }

}
