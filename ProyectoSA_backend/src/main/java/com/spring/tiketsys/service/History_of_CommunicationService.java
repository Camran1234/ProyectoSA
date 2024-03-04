package com.spring.tiketsys.service;

import com.spring.tiketsys.dto.entity.History_of_Communication;
import com.spring.tiketsys.dto.entity.TicketTracking;
import com.spring.tiketsys.dto.entity.compundkeys.History_of_CommunicationId;
import com.spring.tiketsys.dto.model.History_of_CommunicationDTO;
import com.spring.tiketsys.repository.History_of_CommunicationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class History_of_CommunicationService {
    @Autowired
    private History_of_CommunicationRepository historyOfCommunicationRepository;


    public void saveLog(TicketTracking ticketNumber, String activity){
        Date nowDate = new Date();
        System.out.println("saveLog: "+ticketNumber.getTicketNumber());
        int id = generateNextId(ticketNumber.getTicketNumber());
        System.out.println("Next ID: "+id);
        History_of_Communication log = new History_of_Communication(
                id,
                ticketNumber.getTicketNumber(),
                nowDate,
                "",
                "",
                activity
        );
        historyOfCommunicationRepository.saveAndFlush(log);
    }

    @Transactional
    public int generateNextId(int ticketNumber) {
        // Consulta la base de datos para encontrar el máximo valor actual de idHistory para el ticketNumber dado
        Optional<Integer> maxId = historyOfCommunicationRepository.findMaxIdForTicketNumber(ticketNumber);
        int nextId = maxId.orElse(0) + 1;
        return nextId;
    }


    public List<History_of_CommunicationDTO> getLogs(int ticketNumber){
        return getLogs(ticketNumber);
    }
}
