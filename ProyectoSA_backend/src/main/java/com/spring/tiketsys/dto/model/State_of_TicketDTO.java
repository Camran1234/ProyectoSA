package com.spring.tiketsys.dto.model;

public class State_of_TicketDTO {
    private int idState;
    private String state;

    public State_of_TicketDTO(int idState, String state) {
        this.idState = idState;
        this.state = state;
    }

    public State_of_TicketDTO() {
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
