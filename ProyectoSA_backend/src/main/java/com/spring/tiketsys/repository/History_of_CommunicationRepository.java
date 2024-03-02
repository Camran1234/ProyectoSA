package com.spring.tiketsys.repository;

import com.spring.tiketsys.dto.entity.History_of_Communication;
import com.spring.tiketsys.dto.entity.compundkeys.History_of_CommunicationId;
import com.spring.tiketsys.dto.model.History_of_CommunicationDTO;
import com.spring.tiketsys.dto.model.TicketDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface History_of_CommunicationRepository extends JpaRepository<History_of_Communication, History_of_CommunicationId> {

    @Query(nativeQuery = true, value =
            "SELECT * FROM History_of_Communication" +
                    " WHERE ticketNumber= : ticketNumber")
    List<History_of_CommunicationDTO> getLogs(int ticketNumber);


}
