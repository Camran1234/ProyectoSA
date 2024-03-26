package com.spring.tiketsys.repository;

import com.spring.tiketsys.dto.entity.History_of_Communication;
import com.spring.tiketsys.dto.entity.compundkeys.History_of_CommunicationId;
import com.spring.tiketsys.dto.model.History_of_CommunicationDTO;
import com.spring.tiketsys.dto.model.TicketDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface History_of_CommunicationRepository extends JpaRepository<History_of_Communication, History_of_CommunicationId> {

    @Query("SELECT new com.spring.tiketsys.dto.model.History_of_CommunicationDTO(" +
            "    h.idHistory, " +
            "    h.ticketNumber, " +
            "    DATE_FORMAT(h.dateTimeContacted, '%d/%m/%Y %h:%i %p'), " +
            "    h.sent, " +
            "    h.received, " +
            "    h.description" +
            ") " +
            "FROM History_of_Communication h " +
            "WHERE h.ticketNumber = :ticketNumber")
    List<History_of_CommunicationDTO> getLogs(@Param("ticketNumber") int ticketNumber);

    @Query(nativeQuery = true, value=
            "SELECT " +
            "    h.idHistory, " +
            "    h.ticketNumber, " +
            "    DATE_FORMAT(h.dateTimeContacted, '%d/%m/%Y %h:%i %p') AS dateTimeContacted, " +
            "    h.sent, " +
            "    h.received, " +
            "    h.description" +
            "       FROM History_of_Communication h " +
            "    WHERE h.ticketNumber = :ticketNumber")
    List<Map<String,Object>> getLogsNative(@Param("ticketNumber") int ticketNumber);


    @Query(nativeQuery = true, value="SELECT COUNT(*) FROM History_of_Communication h WHERE h.ticketNumber = :ticketNumber")
    Optional<Integer> findMaxIdForTicketNumber(@Param("ticketNumber") int ticketNumber);

}
