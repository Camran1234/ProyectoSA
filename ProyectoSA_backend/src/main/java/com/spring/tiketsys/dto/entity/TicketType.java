package com.spring.tiketsys.dto.entity;

import jakarta.persistence.*;
import lombok.NonNull;

@Entity
@Table(name="TicketType")
public class TicketType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    @Column (name = "idTicketProblem")
    private int idTicketProblem;
    @NonNull
    private String type;

    public TicketType(@NonNull int idTicketProblem, @NonNull String type) {
        this.idTicketProblem = idTicketProblem;
        this.type = type;
    }

    public TicketType() {

    }

    public int getIdTicketProblem() {
        return idTicketProblem;
    }

    public void setIdTicketProblem(int idTicketProblem) {
        this.idTicketProblem = idTicketProblem;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
