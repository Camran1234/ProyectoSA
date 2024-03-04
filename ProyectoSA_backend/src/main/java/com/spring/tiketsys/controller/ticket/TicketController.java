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

import java.util.*;

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
    TicketElementService ticketElementService;
    @Autowired
    JwtChecker jwtChecker;

    @PostMapping("/create-ticket")
    public ResponseEntity<?> createTicket(@Validated @RequestBody String json){
        try{
            JsonNode jsonNode = new JsonOptions().parseStringJsonNode(json);
            //TicketType ticketType = ticketTypeService.getByType(jsonNode.get("ticketType").asText());
            TicketType ticketType = ticketTypeService.findById(jsonNode.get("ticketType").asInt());
            //TicketPriority ticketPriority = ticketPriorityService.getByPriority(jsonNode.get("ticketPriority").asText());
            TicketPriority ticketPriority = ticketPriorityService.findById(jsonNode.get("ticketPriority").asInt());
            // Obtener la lista de URLs del campo "urls"
            JsonNode urlsNode = jsonNode.get("files");
            List<String> urlsList = new ArrayList<>();
            for (JsonNode urlNode : urlsNode) {
                urlsList.add(urlNode.asText());
            }

            System.out.println("createTicket:"+ticketType.getType());
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
            //Creacion del ticket
            ticket = ticketService.createTicket(ticket);
            //Create Elements
            for(String url:urlsList){
                ticketElementService.saveElement(ticket,url);
            }
            //Creacion del tracking
            State_of_Ticket stateOfTicket = stateOfTicketService.getReferencedById(1);
            TicketTracking ticketTracking = ticketTrackingService.createTrack(ticket,stateOfTicket);

            //Creacion del historial
            historyOfCommunicationService.saveLog(
                    ticketTracking,
                    "Se creo el ticket de tipo:"+ticket.getTicketType().getType()+", de prioridad:"+ticket.getPriority().getPriority()
            );

            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch(Exception ex){
            ex.printStackTrace();
            System.out.println("Error: "+ex.getMessage());
            return new ResponseEntity<>(new Message("Error en creacion de tickets: "+ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getTickets")
    public ResponseEntity<?> getTickets(@Validated @RequestParam("email") String email){
        try{
            List<Map<String,Object>> listTokens = ticketService.getTicketsEmail(email);
            List<Map<String, Object>> modifiedList = new ArrayList<>();
            for (Map<String, Object> map : listTokens) {
                Map<String, Object> modifiedMap = new HashMap<>(map); // Crear una copia modificable del mapa actual
                String ticket = modifiedMap.get("ticketNumber").toString();
                int ticketNumber = Integer.parseInt(ticket);
                List<String> elements = ticketService.getTicketsElements(ticketNumber);
                modifiedMap.put("files", elements);
                modifiedList.add(modifiedMap); // Agregar el mapa modificado a la nueva lista
            }

            Map<String, Object> provider = new HashMap<>();
            provider.put("tickets", modifiedList);
            return new ResponseEntity<>(provider, HttpStatus.OK);
        }catch(Exception ex){
            ex.printStackTrace();
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

            TicketTracking ticketTracking = ticketTrackingService.findById(
                    jsonNode.get("ticketNumber").asInt()
            );
            ticketTracking.setDateLastUpdation(new Date());
            ticketTracking = ticketTrackingService.saveTicket(ticketTracking);
            historyOfCommunicationService.saveLog(
                    ticketTracking,
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
