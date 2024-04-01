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

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
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
    @Autowired
    Encrypter encrypter;
    @GetMapping("/")
    public String helloWorld(){
        return "HelloWorld";
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
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Validated @RequestBody String json) {
        try{
            return register(json);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseEntity<>(new Message("Error en registro: "+ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Provide
     * {
     *     username:
     *     password:
     *     userType: [as Role or Type] [options: cliente, agente, administrador]
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
            if (!jwtChecker.initCheck(authorizationHeader) ||
                    !jwtChecker.getSubjectType(authorizationHeader).equalsIgnoreCase("3")) {
                // Registra una sesión inválida
                return new ResponseEntity<>(new Message("Sesión inválida"), HttpStatus.FORBIDDEN);
            }

            return register(json);
        }catch(Exception ex){
            ex.printStackTrace();
            return new ResponseEntity<>(new Message("Error en registro: "+ex.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    private ResponseEntity<?> register(String json) throws TicketException, NoSuchAlgorithmException {
        JsonNode jsonNode = new JsonOptions().parseStringJsonNode(json);
        String passwordEncrypted = encrypter.encrypt(jsonNode.get("password").asText());
        UserType userType = userTypeService.getByType(jsonNode.get("userType").asText());

        if(userService.usernameExists(jsonNode.get("username").asText())){
            throw new TicketException("Usuario ya existe");
        }

        User user = new User(
                jsonNode.get("username").asText(),
                passwordEncrypted,
                jsonNode.get("name").asText(),
                jsonNode.get("lastName").asText(),
                jsonNode.get("phone").asText(),
                userType,
                false
        );
        //Registramos el usuario
        userService.registerUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


    @PostMapping("/get-users")
    public ResponseEntity<?> getUsers(@RequestHeader("Authorization") String authorizationHeader){
        try{
            jwtChecker.checkJWT(authorizationHeader);
            List<Map<String, Object>> users = userService.getAllSecure(jwtChecker.getSubject(authorizationHeader));
            return ResponseEntity.status(HttpStatus.OK).body(users);
        }catch(Exception ex){
            return new ResponseEntity<>(new Message("usuario o contraseña inválidos"), HttpStatus.NOT_ACCEPTABLE);
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
            String passwordEncrypted = encrypter.encrypt(jsonNode.get("password").asText());
            User user = userService.getUser(username);

            if(user.isBlocked()){
                throw new TicketException("el usuario esta bloqueado");
            }

            if(user.getUsername().equalsIgnoreCase(username) &&
            user.getPassword().equals(passwordEncrypted)){
                //handle login
                Map<String, Object> map = new HashMap<>();
                map.put("username", username);
                map.put("user_type", String.valueOf(user.getUserType().getIdUserType()));
                Map<String, Object> response = new HashMap<>();
                response.put("jwt", jwtProvider.generateToken(username, map));
                response.put("user_type", user.getUserType());
                return new ResponseEntity<>(response, HttpStatus.OK);
            }else{
                throw new TicketException("El usuario o la contraseña son incorrectos");
            }
        }catch(Exception ex){
            return new ResponseEntity<>(new Message(ex.getMessage()), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    /**
     * Provided {
     *     username (String)
     *     blocked (int)
     * }
     * @param authorizationHeader
     * @param json
     * @return
     */
    @PostMapping("/changeBlock")
    public ResponseEntity<?> changeBlockUser(@RequestHeader("Authorization") String authorizationHeader,
                                             @Validated @RequestBody String json){
        try{
            jwtChecker.checkJWT(authorizationHeader);
            JsonNode jsonNode = new JsonOptions().parseStringJsonNode(json);
            String username = jsonNode.get("username").asText();
            boolean blocked = (jsonNode.get("blocked").asInt() == 1);
            User user = userService.getUser(username);
            user.setBlocked(blocked);
            userService.registerUser(user);
            return ResponseEntity.status(HttpStatus.OK).body("{}");
        }catch(Exception ex){
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(ex.getMessage()));
        }
    }

    /**
     * Provided: {
     *     username
     * }
     * @param authorizationHeader
     * @param json
     * @return
     */
    @DeleteMapping("/delete-user")
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String authorizationHeader,
                                             @Validated @RequestBody String json){
        try{
            jwtChecker.checkJWT(authorizationHeader);
            JsonNode jsonNode = new JsonOptions().parseStringJsonNode(json);
            String username = jsonNode.get("username").asText();
            User user = userService.getUser(username);
            userService.deleteUser(user);
            return ResponseEntity.status(HttpStatus.OK).body("{}");
        }catch(Exception ex){
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Message(ex.getMessage()));
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateJWT(@RequestHeader("Authorization") String authorizationHeader){
        try{
            jwtChecker.checkJWT(authorizationHeader);
            System.out.println("JWT Valido");
            return new ResponseEntity<>(new Message("Autorizado"), HttpStatus.OK);
        }catch(Exception ex){
            System.out.println("Sesion invalida");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Message("Sesion invalida: "+ex.getMessage()));

        }
    }

    @PostMapping("/get-user")
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String authorizationHeader){
        try{
            jwtChecker.checkJWT(authorizationHeader);
            System.out.println("JWT Valido");
            String subject = jwtChecker.getSubject(authorizationHeader);
            String userType = jwtChecker.getSubjectType(authorizationHeader);
            Map<String, String> response = new HashMap<>();
            response.put("user", subject);
            response.put("userType", userType);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch(Exception ex){
            System.out.println("Sesion Invalida");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new Message("Sesion invalida: "+ex.getMessage()));
        }
    }

}
