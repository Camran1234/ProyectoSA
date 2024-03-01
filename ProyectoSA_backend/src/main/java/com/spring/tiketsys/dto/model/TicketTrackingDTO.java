package com.spring.tiketsys.dto.model;

import java.util.Date;

public class TicketTrackingDTO {
    private int ticketNumber;
    private int state;
    private Date dateofCreation;
    private Date dateLastUpdate;
    private int agent;
    private boolean problemSolved;

    public TicketTrackingDTO() {
    }

    public TicketTrackingDTO(int ticketNumber, int state, Date dateofCreation, Date dateLastUpdate, int agent, boolean problemSolved) {
        this.ticketNumber = ticketNumber;
        this.state = state;
        this.dateofCreation = dateofCreation;
        this.dateLastUpdate = dateLastUpdate;
        this.agent = agent;
        this.problemSolved = problemSolved;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getDateofCreation() {
        return dateofCreation;
    }

    public void setDateofCreation(Date dateofCreation) {
        this.dateofCreation = dateofCreation;
    }

    public Date getDateLastUpdate() {
        return dateLastUpdate;
    }

    public void setDateLastUpdate(Date dateLastUpdate) {
        this.dateLastUpdate = dateLastUpdate;
    }

    public int getAgent() {
        return agent;
    }

    public void setAgent(int agent) {
        this.agent = agent;
    }

    public boolean isProblemSolved() {
        return problemSolved;
    }

    public void setProblemSolved(boolean problemSolved) {
        this.problemSolved = problemSolved;
    }
}
