package com.spring.tiketsys.service;

import com.spring.tiketsys.dto.entity.UserType;
import com.spring.tiketsys.repository.UserTypeRepository;
import com.spring.tiketsys.security.exceptions.TicketException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Tabla estandarizada para el tipo de usuario:
 * - Id: 1, type: cliente (funcional hasta la fase 2)
 * - Id: 2, type: agente
 * - Id: 3, type: administrador
 */
@Service
public class UserTypeService {
    @Autowired
    private UserTypeRepository userTypeRepository;

    //Read by type
    @Transactional
    public UserType getByType(String userType) throws TicketException {
        List<UserType> list = userTypeRepository.findAll();
        for(UserType user:list){
            if(user.getType().equalsIgnoreCase(userType)){
                return user;
            }
        }

        throw new TicketException("Tipo de usuario no detectado, usuario proporcionado: "+userType);
    }

    // Read by user
    @Transactional
    public UserType getById(int idUserType){
        return userTypeRepository.getReferenceById(idUserType);
    }
}
