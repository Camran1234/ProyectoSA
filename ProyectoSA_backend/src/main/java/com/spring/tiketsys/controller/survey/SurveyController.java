package com.spring.tiketsys.controller.survey;


import com.fasterxml.jackson.databind.JsonNode;
import com.spring.tiketsys.config.JsonOptions;
import com.spring.tiketsys.dto.entity.Survey;
import com.spring.tiketsys.dto.entity.Ticket;
import com.spring.tiketsys.dto.model.SurveyDTO;
import com.spring.tiketsys.security.entity.Message;
import com.spring.tiketsys.security.jwt.JwtChecker;
import com.spring.tiketsys.service.SurveyService;
import com.spring.tiketsys.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/survey")
public class SurveyController {
    @Autowired
    SurveyService surveyService;
    @Autowired
    TicketService ticketService;
    @Autowired
    JwtChecker jwtChecker;

    @PostMapping("/saveSurvey")
    public ResponseEntity<?> responseSurver(@Validated @RequestBody String json){
        try{
            JsonNode jsonNode = new JsonOptions().parseStringJsonNode(json);
            int ticketNumber = jsonNode.get("ticketNumber").asInt();
            Ticket ticket = ticketService.getReferenceById(ticketNumber);
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
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch(Exception ex){
            return new ResponseEntity<>(new Message("Error en la respuesta de la encuesta :"+ ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/getSurveys")
    public ResponseEntity<?> getSurveys(@RequestHeader("Authorization") String authorizationHeader){
        try{
            jwtChecker.checkJWT(authorizationHeader);
            return new ResponseEntity<>(surveyService.getSurveys(), HttpStatus.OK);
        }catch(Exception ex){
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
