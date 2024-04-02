package com.spring.tiketsys.repository;

import com.spring.tiketsys.dto.entity.Ticket;
import com.spring.tiketsys.dto.entity.TicketTracking;
import com.spring.tiketsys.dto.model.TicketDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    @Query(nativeQuery = true, value =
            "SELECT  " +
                    "    t.ticketNumber,  " +
                    "    t.email,  " +
                    "    t.name,  " +
                    "    t.lastName,  " +
                    "    t.phone, " +
                    "    t.description,  " +
                    "    t.ticketType,  " +
                    "    t.priority,  " +
                    "    t.owner, " +
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
                    "    t.email = :email  " +
                    "    AND tr.problemSolved = 0 " +
                    "    AND t.owner= :subject ")
    List<Map<String,Object>> getTicketsByUsername(String email, int subject);

    @Query(nativeQuery = true, value =
            "SELECT  " +
                    "    t.ticketNumber,  " +
                    "    t.email,  " +
                    "    t.name,  " +
                    "    t.lastName,  " +
                    "    t.phone, " +
                    "    t.description,  " +
                    "    t.ticketType,  " +
                    "    t.priority,  " +
                    "    t.owner, " +
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
                    "    tr.problemSolved = 0 " +
                    "    AND t.owner= :subject ")
    List<Map<String,Object>> getTicketsSubject(int subject);

    @Query(nativeQuery = true, value =
            "SELECT  " +
                    "    t.ticketNumber,  " +
                    "    t.email,  " +
                    "    t.name,  " +
                    "    t.lastName,  " +
                    "    t.phone, " +
                    "    t.description,  " +
                    "    t.ticketType,  " +
                    "    t.priority,  " +
                    "    t.owner, " +
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
                    "    tr.problemSolved = 1 " +
                    "    AND tr.surveyAvailable = 0 " +
                    "    AND t.owner= :subject ")
    List<Map<String,Object>> getTicketsWithoutSurvey(int subject);

    @Query(nativeQuery = true, value =
            "SELECT  " +
                    "    t.ticketNumber,  " +
                    "    t.email,  " +
                    "    t.name,  " +
                    "    t.lastName,  " +
                    "    t.phone, " +
                    "    t.description,  " +
                    "    t.ticketType,  " +
                    "    t.priority,  " +
                    "    t.owner, " +
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
                    "    t.ticketNumber = :ticketNumber  " +
                    "    AND tr.problemSolved = 0;")
    Map<String,Object> getTicketByTicketNumber(int ticketNumber);

    @Query(nativeQuery = true, value =
            "SELECT  " +
                    "    t.ticketNumber,  " +
                    "    t.email,  " +
                    "    t.name,  " +
                    "    t.lastName,  " +
                    "    t.phone, " +
                    "    t.description,  " +
                    "    t.ticketType,  " +
                    "    t.priority,  " +
                    "    DATE_FORMAT(tr.dateofCreation, '%d/%m/%Y') AS dateOfCreation, " +
                    "    DATE_FORMAT(tr.dateLastUpdate, '%d/%m/%Y') AS dateLastUpdate " +
                    "FROM  " +
                    "    Ticket t " +
                    "INNER JOIN  " +
                    "    TicketTracking tr ON t.ticketNumber = tr.ticketNumber " +
                    "WHERE  " +
                    "    tr.agent IS NULL;")
    List<Map<String,Object>> getUnsolvedTickets();



}
