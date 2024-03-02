package com.spring.tiketsys.security.jwt;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Map;

@Component
public class JwtProvider {
    private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${jwt.secret}")
    private String secret;
    //Horas
    @Value("${jwt.expiration}")
    private int expiration;

    /**
     * Metodo para generar un token
     * el jwtSubject es el quien maneja la firma y claims los datos que se pueden agregar dentro del token
     * @param jwtSubject
     * @param claims
     * @return
     * @throws NoSuchAlgorithmException
     */
    public String generateToken(String jwtSubject, Map<String, Object> claims) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        byte[] secretBytes = secret.getBytes(StandardCharsets.UTF_8);
        Key secretKey = new SecretKeySpec(secretBytes, SignatureAlgorithm.HS256.getJcaName());
        return Jwts.builder()
                .setSubject(jwtSubject)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()  + expiration * 3600000))
                .signWith(secretKey)
                .compact();
    }

    /**
     * Patron para verificar un token
     * @param token
     * @return
     */
    public boolean verifyJwt(String token) throws Exception{
        try {
            token = token.split(" ")[1].trim();
            byte[] secretBytes = secret.getBytes(StandardCharsets.UTF_8);
            Key secretKey = new SecretKeySpec(secretBytes, SignatureAlgorithm.HS256.getJcaName());

            //Checking Expiration
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            Date expirationDate = claims.getExpiration();
            Date currentDate = new Date();
            if(expirationDate != null && expirationDate.before(currentDate)){
                return false;
            }
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Token mal formado");
            throw new Exception("Token mal formado");
        } catch (UnsupportedJwtException e) {
            logger.error("Token no soportado");
            throw new Exception("Token no soportado");
        } catch (ExpiredJwtException e) {
            //Eliminamos el token
            logger.error("Token expirado");
            throw new Exception("Token expirado");
        } catch (IllegalArgumentException e) {
            logger.error("Token vacio");
            throw new Exception("Token vacio");
        }
    }


}
