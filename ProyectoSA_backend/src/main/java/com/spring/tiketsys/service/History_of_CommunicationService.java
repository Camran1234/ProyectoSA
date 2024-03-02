package com.spring.tiketsys.service;

import com.spring.tiketsys.dto.entity.History_of_Communication;
import com.spring.tiketsys.dto.model.History_of_CommunicationDTO;
import com.spring.tiketsys.repository.History_of_CommunicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class History_of_CommunicationService {
    @Autowired
    private History_of_CommunicationRepository historyOfCommunicationRepository;


    public void saveLog(int ticketNumber, String activity){
        Date nowDate = new Date();
        History_of_Communication log = new History_of_Communication(
                ticketNumber,
                nowDate,
                "",
                "",
                activity
        );
        historyOfCommunicationRepository.saveAndFlush(log);
    }

    public List<History_of_CommunicationDTO> getLogs(int ticketNumber){
        return getLogs(ticketNumber);
    }
}
