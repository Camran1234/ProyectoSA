package com.spring.tiketsys.repository;

import com.spring.tiketsys.dto.entity.Ticket;
import com.spring.tiketsys.dto.entity.TicketTracking;
import com.spring.tiketsys.dto.model.TicketDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    @Query(nativeQuery = true, value =
            "SELECT t.ticketNumber, t.email, t.name, t.lastName, t.phone," +
                    "            t.description, t.ticketType, t.priority, t.owner" +
                    "    FROM Ticket t" +
                    "    INNER JOIN TicketTracking tr" +
                    "            ON t.ticketNumber=tr.ticketNumber" +
                    "    WHERE" +
                    "            t.email= :email AND" +
                    "            tr.problemSolved=0")
    List<TicketDTO> getTicketsByUsername(String email);



}
