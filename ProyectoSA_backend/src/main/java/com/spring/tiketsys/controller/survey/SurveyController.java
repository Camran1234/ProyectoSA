package com.spring.tiketsys.controller.survey;


import com.fasterxml.jackson.databind.JsonNode;
import com.spring.tiketsys.config.JsonOptions;
import com.spring.tiketsys.dto.entity.Survey;
import com.spring.tiketsys.dto.entity.Ticket;
import com.spring.tiketsys.dto.entity.TicketTracking;
import com.spring.tiketsys.dto.model.SurveyDTO;
import com.spring.tiketsys.security.entity.Message;
import com.spring.tiketsys.security.jwt.JwtChecker;
import com.spring.tiketsys.service.SurveyService;
import com.spring.tiketsys.service.TicketService;
import com.spring.tiketsys.service.TicketTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/survey")
public class SurveyController {
    @Autowired
    SurveyService surveyService;
    @Autowired
    TicketService ticketService;
    @Autowired
    TicketTrackingService ticketTrackingService;
    @Autowired
    JwtChecker jwtChecker;


    @PostMapping("/saveSurvey")
    public ResponseEntity<?> responseSurvey(@RequestHeader("Authorization") String authorizationHeader,
                                            @Validated @RequestBody String json){
        try{
            System.out.println("Iniciando saveSurvey");
            jwtChecker.checkJWT(authorizationHeader);
            JsonNode jsonNode = new JsonOptions().parseStringJsonNode(json);
            int ticketNumber = jsonNode.get("ticketNumber").asInt();
            Ticket ticket = ticketService.getReferenceById(ticketNumber);
            TicketTracking ticketTracking = ticketTrackingService.findById(ticketNumber);
            ticketTracking.setSurveyAvailable(true);
            int satisfaction = jsonNode.get("satisfaction").asInt();
            int timeService = jsonNode.get("timeService").asInt();
            int qualityService = jsonNode.get("qualityService").asInt();
            Survey survey = new Survey(
                    ticket,
                    satisfaction,
                    timeService,
                    qualityService
            );
            surveyService.saveSurvey(survey);
            ticketTrackingService.saveTicket(ticketTracking);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch(Exception ex){
            ex.printStackTrace();
            System.out.println("Error! "+ex.getMessage());
            return new ResponseEntity<>(new Message("Error en la respuesta de la encuesta :"+ ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/getDataReport")
    public ResponseEntity<?> getSurveys(@RequestHeader("Authorization") String authorizationHeader){
        try{
            jwtChecker.checkJWT(authorizationHeader);
            Map<String, Object> response = new HashMap<>();

            response.put("unsolvedTickets", ticketService.getTicketsUnsolved());
            response.put("unqualifiedTickets", ticketService.getTicketsUnqualified());
            response.put("surveys", surveyService.getSurveys());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch(Exception ex){
            ex.printStackTrace();
            return  new ResponseEntity<>(new Message("Error al obtener las encuestas"), HttpStatus.BAD_REQUEST);
        }
    }



    @PostMapping("/getCSV")
    public ResponseEntity<?> getCSV(@RequestHeader("Authorization") String authorizationHeader){
        try{
            jwtChecker.checkJWT(authorizationHeader);
            return new ResponseEntity<>(surveyService.getCsv(), HttpStatus.OK);
        }catch(Exception ex){
            return new ResponseEntity<>(new Message("Error en obtener el csv"), HttpStatus.BAD_REQUEST);
        }
    }
}
