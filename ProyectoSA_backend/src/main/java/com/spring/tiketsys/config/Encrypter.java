package com.spring.tiketsys.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Class to Encrypt and Desencrypt the passwords of users
 * TO FIX: Add salt to the database and not in the system
 */
@Component
public class Encrypter {

    @Value("${password.salt}")
    private String keyword;

    private byte[] generateSaltFromKeyword(){
        return keyword.getBytes(StandardCharsets.UTF_8);
    }

    public String encrypt(String password) throws NoSuchAlgorithmException{
        //Generating salt
        byte[] salt = generateSaltFromKeyword();
        //Digest salt
        MessageDigest md = null;
        md = MessageDigest.getInstance("SHA-512");
        md.update(salt);
        //Generate hashed password
        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));
        return new String(hashedPassword);
    }

    public boolean comparePasswords(String password, String hashedPassword) throws NoSuchAlgorithmException{
        return hashedPassword.equals(encrypt(password));
    }

}
