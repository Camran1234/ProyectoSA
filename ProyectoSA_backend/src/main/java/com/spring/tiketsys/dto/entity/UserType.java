package com.spring.tiketsys.dto.entity;

import jakarta.persistence.*;
import lombok.NonNull;

@Entity
@Table(name="UserType")
public class UserType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idUserTpe")
    @NonNull
    private int idUserType;
    @NonNull
    private String type;

    public UserType() {
    }
    public UserType(@NonNull int idUserType, @NonNull String type) {
        this.idUserType = idUserType;
        this.type = type;
    }

    public int getIdUserType() {
        return idUserType;
    }

    public void setIdUserType(int idUserType) {
        this.idUserType = idUserType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
