package com.spring.tiketsys.service;

import com.spring.tiketsys.dto.entity.History_of_Communication;
import com.spring.tiketsys.dto.entity.TicketTracking;
import com.spring.tiketsys.dto.entity.compundkeys.History_of_CommunicationId;
import com.spring.tiketsys.dto.model.History_of_CommunicationDTO;
import com.spring.tiketsys.repository.History_of_CommunicationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class History_of_CommunicationService {
    @Autowired
    private History_of_CommunicationRepository  historyOfCommunicationRepository;

    public History_of_CommunicationDTO saveMessage(int ticketNumber, String message, String sent, Date nowDate){

        int id = generateNextId(ticketNumber);
        History_of_Communication log = new History_of_Communication(
            id,
            ticketNumber,
            nowDate,
            sent,
            "",
            message
        );
        History_of_Communication result = historyOfCommunicationRepository.saveAndFlush(log);
        return result.parseToDTO();
    }

    @Transactional
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



    public List<Map<String,Object>> getLogs(int ticketNumber){
        /*List<History_of_Communication> historyOfCommunications = historyOfCommunicationRepository.getLogs(ticketNumber);
        List<History_of_CommunicationDTO> communicationDTOS = new ArrayList<>();
        for(History_of_Communication historyOfCommunication: historyOfCommunications){
            communicationDTOS.add(historyOfCommunication.parseToDTO());
        }*/
        return historyOfCommunicationRepository.getLogsNative(ticketNumber);
    }
}
