package com.spring.tiketsys.dto.entity;

import jakarta.persistence.*;
import lombok.NonNull;

@Entity
@Table(name="State_of_Ticket")
public class State_of_Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    @Column (name = "idState")
    private int idState;
    @NonNull
    private String state;

    public State_of_Ticket() {
    }
    public State_of_Ticket(@NonNull int idState, @NonNull String state) {
        this.idState = idState;
        this.state = state;
    }

    public int getIdState() {
        return idState;
    }

    public void setIdState(int idState) {
        this.idState = idState;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
