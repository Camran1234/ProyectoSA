package com.spring.tiketsys.controller.ticket;

import com.fasterxml.jackson.databind.JsonNode;
import com.spring.tiketsys.config.JsonOptions;
import com.spring.tiketsys.dto.entity.History_of_Communication;
import com.spring.tiketsys.dto.entity.State_of_Ticket;
import com.spring.tiketsys.dto.entity.TicketTracking;
import com.spring.tiketsys.dto.entity.User;
import com.spring.tiketsys.dto.model.History_of_CommunicationDTO;
import com.spring.tiketsys.security.entity.Message;
import com.spring.tiketsys.security.exceptions.TicketException;
import com.spring.tiketsys.security.jwt.JwtChecker;
import com.spring.tiketsys.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api/tracking")
public class TicketTrackingController {
    @Autowired
    TicketTrackingService ticketTrackingService;
    @Autowired
    TicketService ticketService;
    @Autowired
    State_of_TicketService stateOfTicketService;
    @Autowired
    History_of_CommunicationService historyOfCommunicationService;
    @Autowired
    UserService userService;
    @Autowired
    JwtChecker jwtChecker;


    @PostMapping("/change-state")
    public ResponseEntity<?> changeTicketState(@Validated @RequestBody String json,
                                               @RequestHeader("Authorization") String authorizationHeader){
        try{
            if(!jwtChecker.initCheck(authorizationHeader)){
                return new ResponseEntity<>(new Message("Sesion invalida"), HttpStatus.FORBIDDEN);
            }
            JsonNode jsonNode = new JsonOptions().parseStringJsonNode(json);
            // Find the new Priority
            State_of_Ticket stateOfTicket = stateOfTicketService.getReferencedById(
                    jsonNode.get("state").asInt()
            );
            //Find the ticket
            TicketTracking ticketTracking = ticketTrackingService.findById(
                    jsonNode.get("ticketNumber").asInt()
            );
            ticketTracking.setState(stateOfTicket);
            ticketTracking.setDateLastUpdation(new Date());
            ticketTracking=ticketTrackingService.saveTicket(ticketTracking);
            historyOfCommunicationService.saveLog(
                    ticketTracking,
                    "Se cambio el estado del ticket a "+(jsonNode.get("state").asText())
            );
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch(Exception ex){
            return new ResponseEntity<>(new Message("Error en cambio de estado del ticket: "+ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/solve")
    public ResponseEntity<?> solve(@Validated @RequestBody String json,
                             @RequestHeader("Authorization") String authorizationHeader){
        try{
            if(!jwtChecker.initCheck(authorizationHeader)){
                return new ResponseEntity<>(new Message("Sesion invalida"), HttpStatus.FORBIDDEN);
            }
            JsonNode jsonNode = new JsonOptions().parseStringJsonNode(json);
            TicketTracking ticketTracking = ticketTrackingService.findById(
                    jsonNode.get("ticketNumber").asInt()
            );
            ticketTracking.setProblemSolved(true);
            State_of_Ticket state = stateOfTicketService.getByState("cerrado");
            ticketTracking.setState(state);
            ticketTracking.setDateLastUpdation(new Date());
            ticketTracking=ticketTrackingService.saveTicket(ticketTracking);
            historyOfCommunicationService.saveLog(
                    ticketTracking,
                    "Se resolvio el ticket"
            );
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch(Exception ex){
            return new ResponseEntity<>(new Message("Error al resolver el ticket: "+ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/trackLogs")
    public ResponseEntity<?> getLogs(@Validated @RequestBody String json){
        try{
            JsonNode jsonNode = new JsonOptions().parseStringJsonNode(json);
            int ticketNumber = jsonNode.get("ticketNumber").asInt();
            List<Map<String,Object>> list = historyOfCommunicationService.getLogs(ticketNumber);
            return new ResponseEntity<>(list, HttpStatus.OK);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseEntity<>(new Message("Error, no se pudieron obtener los logs: "+ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/assign-agent")
    public ResponseEntity<?> assignAgent(@Validated @RequestBody String json,
                                         @RequestHeader("Authorization") String authorizationHeader){
        try{
            if (!jwtChecker.initCheck(authorizationHeader)) {
                // Registra una sesión inválida
                return new ResponseEntity<>(new Message("Sesión inválida"), HttpStatus.FORBIDDEN);
            }
            JsonNode jsonNode = new JsonOptions().parseStringJsonNode(json);
            String agentUsername = jwtChecker.getSubject(authorizationHeader);
            User agent = userService.getUser(agentUsername);
            TicketTracking ticketTracking =  ticketTrackingService.findById(jsonNode.get("ticketNumber").asInt());
            ticketTracking.setAgent(agent);
            State_of_Ticket stateOfTicket = stateOfTicketService.getReferencedById(2);
            ticketTracking.setState(stateOfTicket);
            ticketTracking.setDateLastUpdation(new Date());
            ticketTracking = ticketTrackingService.saveTicket(ticketTracking);
            historyOfCommunicationService.saveLog(
                    ticketTracking,
                    "El ticket se asigno a "+ticketTracking.getAgent().getName()+
                            " "+ticketTracking.getAgent().getLastName()+" usuario "+ticketTracking.getAgent().getUsername()
            );
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch(Exception ex){
            return new ResponseEntity<>(new Message("Error en cambio de agente en ticket: "+ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get-agent-all")
    public ResponseEntity<?> getAgentTickets(@RequestHeader("Authorization") String authorizationHeader){
        try{
            if (!jwtChecker.initCheck(authorizationHeader)||
                    !jwtChecker.getSubjectType(authorizationHeader).equalsIgnoreCase("2")) {
                // Registra una sesión inválida
                return new ResponseEntity<>(new Message("Sesión inválida"), HttpStatus.FORBIDDEN);
            }
            User agent = userService.getUser(jwtChecker.getSubject(authorizationHeader));
            List<Map<String, Object>> listTokens = ticketTrackingService.getAgentTickets(agent.getIdUser());

            List<Map<String, Object>> modifiedList = new ArrayList<>();
            for (Map<String, Object> map : listTokens) {
                Map<String, Object> modifiedMap = new HashMap<>(map); // Crear una copia modificable del mapa actual
                String ticket = modifiedMap.get("ticketNumber").toString();
                int ticketNumber = Integer.parseInt(ticket);
                List<String> elements = ticketService.getTicketsElements(ticketNumber);
                modifiedMap.put("files", elements);
                modifiedList.add(modifiedMap); // Agregar el mapa modificado a la nueva lista
            }


            return ResponseEntity.status(HttpStatus.OK).body(modifiedList);
        }catch(Exception ex){
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message("No se pudo obtener los tickets"));
        }
    }

    @PostMapping("/visualize")
    public ResponseEntity<?> visualize(@Validated @RequestBody String json){
        try{
            JsonNode jsonNode = new JsonOptions().parseStringJsonNode(json);
            int ticketNumber = jsonNode.get("ticketNumber").asInt();
            TicketTracking ticketTracking = ticketTrackingService.findById(ticketNumber);
            return new ResponseEntity<>(ticketTracking, HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>(new Message("Error en obtencion de datos del cliente "+ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/visualizeTicket")
    public ResponseEntity<?> viewTicket(@Validated @RequestBody String json,
                                        @RequestHeader("Authorization") String authorizationHeader){
        try{
            //Validacion del JWT
            jwtChecker.checkJWT(authorizationHeader);

            JsonNode jsonNode = new JsonOptions().parseStringJsonNode(json);
            int ticketNumber = jsonNode.get("ticketNumber").asInt();
            //Obtener El track del ticket y el historial
            TicketTracking ticketTracking = ticketTrackingService.findById(ticketNumber);
            List<Map<String,Object>> historyOfCommunication = historyOfCommunicationService.getLogs(ticketNumber);

            //Armar la respuesta
            Map<String, Object> response = new HashMap<>();
            response.put("ticket", ticketTracking);
            response.put("history", historyOfCommunication);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>(new Message("Error en obtencion de datos del cliente "+ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/saveMessage")
    public ResponseEntity<?> saveMessage(@Validated @RequestBody String json){
        try{
            JsonNode jsonNode = new JsonOptions().parseStringJsonNode(json);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            String dateString = jsonNode.get("date").asText();
            History_of_CommunicationDTO dto = historyOfCommunicationService.saveMessage(
                    jsonNode.get("ticketNumber").asInt(),
                    jsonNode.get("description").asText(),
                    jsonNode.get("sent").asText(),
                    dateFormat.parse(dateString)
            );
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }catch(Exception ex){

            ex.printStackTrace();
            return new ResponseEntity<>(new Message("No se pudo enviar el mensaje "+ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }
}
