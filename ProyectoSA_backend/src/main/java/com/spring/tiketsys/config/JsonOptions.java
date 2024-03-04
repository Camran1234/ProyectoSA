package com.spring.tiketsys.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.tiketsys.security.exceptions.TicketException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class JsonOptions {

    public JsonNode parseStringJsonNode(String json) throws TicketException {
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(json);
            return jsonNode;
        }catch(Exception ex){
            throw new TicketException("Error en el cuerpo del mensaje: "+ex.getMessage());
        }
    }
}
