package com.spring.tiketsys.controller.user;

import com.fasterxml.jackson.databind.JsonNode;
import com.spring.tiketsys.config.Encrypter;
import com.spring.tiketsys.config.JsonOptions;
import com.spring.tiketsys.dto.entity.User;
import com.spring.tiketsys.dto.entity.UserType;
import com.spring.tiketsys.security.entity.Message;
import com.spring.tiketsys.security.exceptions.TicketException;
import com.spring.tiketsys.security.jwt.JwtChecker;
import com.spring.tiketsys.security.jwt.JwtProvider;
import com.spring.tiketsys.service.UserService;
import com.spring.tiketsys.service.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserTypeService userTypeService;
    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    JwtChecker jwtChecker;
    /**
     * Provide
     * {
     *     username:
     *     password:
     *     userType: [as Role or Type]
     *     name:
     *     lastName:
     *     phone:
     * }
     * @param json
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Validated @RequestBody String json) {
        try{
            JsonNode jsonNode = new JsonOptions().parseStringJsonNode(json);
            String passwordEncrypted = new Encrypter().encrypt(jsonNode.get("password").asText());
            UserType userType = userTypeService.getByType(jsonNode.get("userType").asText());

            if(userService.usernameExists(jsonNode.get("username").asText())){
                throw new TicketException("Usuario en uso");
            }

            User user = new User(
                    jsonNode.get("username").asText(),
                    passwordEncrypted,
                    jsonNode.get("name").asText(),
                    jsonNode.get("lastName").asText(),
                    jsonNode.get("phone").asText(),
                    userType
            );
            //Registramos el usuario
            userService.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch(Exception ex){
            return new ResponseEntity<>(new Message("Error en registro: "+ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Provide
     * {
     *     username:
     *     password:
     *     userType: [as Role or Type]
     *     name:
     *     lastName:
     *     phone:
     * }
     * @param json
     * @return
     */
    @PostMapping("/registerSpecial")
    public ResponseEntity<?> registerUserProtected(@Validated @RequestBody String json,
                                                   @RequestHeader("Authorization") String authorizationHeader) {
        try{
            if (!jwtChecker.initCheck(authorizationHeader)) {
                // Registra una sesión inválida
                return new ResponseEntity<>(new Message("Sesión inválida"), HttpStatus.FORBIDDEN);
            }

            JsonNode jsonNode = new JsonOptions().parseStringJsonNode(json);
            String passwordEncrypted = new Encrypter().encrypt(jsonNode.get("password").asText());
            UserType userType = userTypeService.getByType(jsonNode.get("userType").asText());

            if(userService.usernameExists(jsonNode.get("username").asText())){
                throw new TicketException("Usuario en uso");
            }

            User user = new User(
                    jsonNode.get("username").asText(),
                    passwordEncrypted,
                    jsonNode.get("name").asText(),
                    jsonNode.get("lastName").asText(),
                    jsonNode.get("phone").asText(),
                    userType
            );
            //Registramos el usuario
            userService.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch(Exception ex){
            return new ResponseEntity<>(new Message("Error en registro: "+ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Provide:{
     *     username: String
     *     password: String
     * }
     *
     * Returns: {
     *     token: String
     *     user_type: number
     * }
     * @param json
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody String json){
        try{
            JsonNode jsonNode = new JsonOptions().parseStringJsonNode(json);
            String username = jsonNode.get("username").asText();
            String passwordEncrypted = new Encrypter().encrypt(jsonNode.get("password").asText());
            User user = userService.getUser(username);
            if(user.getUsername().equalsIgnoreCase(username) &&
            user.getPassword().equals(passwordEncrypted)){
                //handle login
                Map<String, Object> map = new HashMap<>();
                map.put("username", username);
                Map<String, Object> response = new HashMap<>();
                response.put("jwt", jwtProvider.generateToken(username, map));
                response.put("user_type", user.getUserType());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }else{
                throw new TicketException("El usuario o la contraseña son incorrectos");
            }
        }catch(Exception ex){
            return new ResponseEntity<>(new Message("Error en Inicio de sesion: "+ex.getMessage()), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateJWT(@RequestHeader("Authorization") String authorizationHeader){
        try{
            jwtChecker.checkJWT(authorizationHeader);
            return ResponseEntity.status(HttpStatus.OK).build();
        }catch(Exception ex){
            return new ResponseEntity<>(new Message("Sesion invalida: ")+ex.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

}
