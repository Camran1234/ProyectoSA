package com.spring.tiketsys.repository;

import com.spring.tiketsys.dto.entity.History_of_Communication;
import com.spring.tiketsys.dto.entity.compundkeys.History_of_CommunicationId;
import com.spring.tiketsys.dto.model.History_of_CommunicationDTO;
import com.spring.tiketsys.dto.model.TicketDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface History_of_CommunicationRepository extends JpaRepository<History_of_Communication, History_of_CommunicationId> {

    @Query(nativeQuery = true, value =
            "SELECT * FROM History_of_Communication" +
                    " WHERE ticketNumber= : ticketNumber")
    List<History_of_CommunicationDTO> getLogs(int ticketNumber);

    @Query(nativeQuery = true, value="SELECT COUNT(*) FROM History_of_Communication h WHERE h.ticketNumber = :ticketNumber")
    Optional<Integer> findMaxIdForTicketNumber(@Param("ticketNumber") int ticketNumber);

}
