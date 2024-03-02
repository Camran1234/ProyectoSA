package com.spring.tiketsys.security.jwt;

import com.spring.tiketsys.security.exceptions.TicketException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;


/**
 * Class to do protected methods
 */
@Component
public class JwtChecker{

    @Value("${jwt.secret}")
    String secret;
    @Autowired
    JwtProvider jwtProvider;


    /**
     * Main method to verify a token
     * @param token
     * @return
     * @throws Exception
     */
    public boolean initCheck(String token) throws Exception{
        try {
            return this.checkSession(token);
        } catch (NoSuchAlgorithmException e) {
            return false;
        }
    }

    public void checkJWT(String token) throws TicketException {
        try{
            this.checkSession(token);
        }catch(Exception ex){
            throw new TicketException("Sesion Invalida");
        }
    }

    /**
     * Obtiene el subject del token
     * @param token
     * @return
     */
    public String getSubject(String token) throws NoSuchAlgorithmException {
        try {
            token = token.split(" ")[1].trim();
            byte[] secretBytes = secret.getBytes(StandardCharsets.UTF_8);
            Key secretKey = new SecretKeySpec(secretBytes, SignatureAlgorithm.HS256.getJcaName());
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return Long.toString(claims.get("username", Long.class));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Obtiene la expiracion del token
     * @param token
     * @return
     */
    public String getTokenExpiration(String token) throws NoSuchAlgorithmException {
        try {
            token = token.split(" ")[1].trim();
            byte[] secretBytes = secret.getBytes(StandardCharsets.UTF_8);
            Key secretKey = new SecretKeySpec(secretBytes, SignatureAlgorithm.HS256.getJcaName());
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            String dateFormatPattern = "dd/MM/yyyy HH:mm:ss";

            // Create a SimpleDateFormat instance with the desired format
            SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatPattern);
            return dateFormat.format(claims.getExpiration());
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Regresa true si el token aun esta en sesion
     * Regresa false si no esta en sesion
     * @param token
     * @return
     * @throws JwtException
     * @throws NoSuchAlgorithmException
     */
    public boolean checkSession(String token) throws Exception {
        String result = this.getSubject(token);
        if(result== null){
            throw new Exception("Sesion Invalida");
        }
        long user_id = Long.parseLong(result);
        if(user_id<=0){
            throw new JwtException("Error: user_id no reconocible");
        }
        /*
        Method only supported if user had a token method
        CredentialDTO credentialDTO = credentialRepository.findCredentialDTOById(user_id);
        Credential credential = DTOConverter.convertToCredentialEntity(credentialDTO);
        if(credential.getUser_token() == null || credential.getUser_token().isBlank() ||
                credential.getUser_token().isEmpty()){
            throw new JwtException("No existe el token");
        }*/
        return jwtProvider.verifyJwt(token);
    }
}
