package com.spring.tiketsys.controller.ticket;

import com.fasterxml.jackson.databind.JsonNode;
import com.spring.tiketsys.config.JsonOptions;
import com.spring.tiketsys.dto.entity.*;
import com.spring.tiketsys.dto.model.TicketDTO;
import com.spring.tiketsys.security.entity.Message;
import com.spring.tiketsys.security.jwt.JwtChecker;
import com.spring.tiketsys.service.*;
import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/ticket")
public class TicketController {
    @Autowired
    TicketService ticketService;
    @Autowired
    TicketTrackingService ticketTrackingService;
    @Autowired
    TicketTypeService ticketTypeService;
    @Autowired
    TicketPriorityService ticketPriorityService;
    @Autowired
    State_of_TicketService stateOfTicketService;
    @Autowired
    History_of_CommunicationService historyOfCommunicationService;
    @Autowired
    JwtChecker jwtChecker;

    @PostMapping("/create-ticket")
    public ResponseEntity<?> createTicket(@Validated @RequestBody String json){
        try{
            JsonNode jsonNode = new JsonOptions().parseStringJsonNode(json);
            TicketType ticketType = ticketTypeService.getByType(jsonNode.get("ticketType").asText());
            TicketPriority ticketPriority = ticketPriorityService.getByPriority(jsonNode.get("ticketPriority").asText());
            Ticket ticket = new Ticket(
                    jsonNode.get("email").asText(),
                    jsonNode.get("name").asText(),
                    jsonNode.get("lastName").asText(),
                    jsonNode.get("phone").asText(),
                    jsonNode.get("description").asText(),
                    ticketType,
                    ticketPriority,
                    null
            );
            ticketService.createTicket(ticket);
            historyOfCommunicationService.saveLog(
                    ticket.getTicketNumber(),
                    "Se creo el ticket de tipo:"+ticket.getTicketType().getType()+", de prioridad:"+ticket.getPriority().getPriority()
            );
            //Por default el 1 es el estado de nuevo
            State_of_Ticket stateOfTicket = stateOfTicketService.getReferencedById(1);
            ticketTrackingService.createTrack(ticket,stateOfTicket);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch(Exception ex){
            return new ResponseEntity<>(new Message("Error en creacion de tickets: "+ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getTickets")
    public ResponseEntity<?> getTickets(@Validated @RequestBody String json){
        try{
            JsonNode jsonNode = new JsonOptions().parseStringJsonNode(json);
            String email = jsonNode.get("email").asText();
            List<TicketDTO> listTokens = ticketService.getTicketsEmail(email);
            Map<String, Object> provider = new HashMap<>();
            provider.put("tickets", listTokens);
            return new ResponseEntity<>(provider, HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>(new Message("Error obtencion de tickets: "+ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/change-priority")
    public ResponseEntity<?> changeTicketPriority(@Validated @RequestBody String json){
        try{
            JsonNode jsonNode = new JsonOptions().parseStringJsonNode(json);
            // Find the new Priority
            TicketPriority priority = ticketPriorityService.getByPriority(
                    jsonNode.get("priority").asText()
            );
            //Find the ticket
            Ticket ticket = ticketService.getReferenceById(
                    jsonNode.get("ticketNumber").asInt()
            );

            ticket.setPriority(priority);
            ticketService.createTicket(ticket);

            TicketTracking ticketTracking = ticketTrackingService.getReferencedById(
                    jsonNode.get("ticketNumber").asInt()
            );
            ticketTracking.setDateLastUpdation(new Date());
            ticketTrackingService.saveTicket(ticketTracking);
            historyOfCommunicationService.saveLog(
                    jsonNode.get("ticketNumber").asInt(),
                    "Se cambio la prioridad del ticket a "+jsonNode.get("priority").asText()
            );
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch(Exception ex){
            return new ResponseEntity<>(new Message("Error en cambio de prioridad en ticket: "+ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }




    @PostMapping("/assign-owner")
    public ResponseEntity<?> assignOwner(@Validated @RequestBody String json){
        try{
            //Deprecated
            return null;
        }catch(Exception ex){
            return new ResponseEntity<>(new Message("Error en cambio de prioridad en ticket: "+ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }


}
