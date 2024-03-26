package com.spring.tiketsys.repository;

import com.spring.tiketsys.dto.entity.TicketTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface TicketTrackingRepository extends JpaRepository<TicketTracking, Integer> {

    @Query(nativeQuery = true, value=
            "SELECT  " +
                    "    t.ticketNumber,  " +
                    "    t.email,  " +
                    "    t.name,  " +
                    "    t.lastName,  " +
                    "    t.phone, " +
                    "    t.description,  " +
                    "    t.ticketType,  " +
                    "    t.priority,  " +
                    "    tr.state,  " +
                    "    DATE_FORMAT(tr.dateofCreation, '%d/%m/%Y') AS dateOfCreation, " +
                    "    DATE_FORMAT(tr.dateLastUpdate, '%d/%m/%Y') AS dateLastUpdate, " +
                    "    JSON_OBJECT( " +
                    "        'username', u.username, " +
                    "        'name', u.name, " +
                    "        'lastName', u.lastName, " +
                    "        'phone', u.phone, " +
                    "        'userType', u.userType " +
                    "    ) AS agent, " +
                    "    tr.problemSolved " +
                    "FROM  " +
                    "    Ticket t " +
                    "INNER JOIN  " +
                    "    TicketTracking tr ON t.ticketNumber = tr.ticketNumber " +
                    "LEFT JOIN " +
                    "    User u ON tr.agent = u.idUser " +
                    "WHERE  " +
                    "    tr.agent= :agent AND tr.problemSolved = 0")
    List<Map<String, Object>> getAgentTickets(int agent);
}
