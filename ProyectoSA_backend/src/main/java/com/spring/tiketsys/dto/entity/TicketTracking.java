package com.spring.tiketsys.dto.entity;

import com.spring.tiketsys.dto.ParserEntity;
import com.spring.tiketsys.dto.entity.compundkeys.History_of_CommunicationId;
import com.spring.tiketsys.dto.model.TicketTrackingDTO;
import jakarta.persistence.*;
import lombok.NonNull;

import java.util.Date;

@Entity
@Table(name="TicketTracking")
public class TicketTracking implements ParserEntity {
    @Id
    @NonNull
    @Column(name="ticketNumber")
    private int ticketNumber;
    @NonNull
    @ManyToOne
    @JoinColumn(name="state", referencedColumnName = "idState")
    private State_of_Ticket state;
    @NonNull
    private Date dateofCreation;
    @NonNull
    private Date dateLastUpdate;
    @ManyToOne
    @JoinColumn(name = "agent", referencedColumnName = "idUser")
    private User agent;
    @NonNull
    private boolean problemSolved;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="ticketNumber", referencedColumnName = "ticketNumber", insertable = false, updatable = false)
    private Ticket ticketNumberObject;

    public TicketTracking() {
    }
    public TicketTracking(@NonNull int ticketNumber, @NonNull State_of_Ticket state, @NonNull Date dateOfCreation, Date dateLastUpdation, User agent, @NonNull boolean problemSolved, Ticket ticketNumberObject) {
        this.ticketNumber = ticketNumber;
        this.state = state;
        this.dateofCreation = dateOfCreation;
        this.dateLastUpdate = dateLastUpdation;
        this.agent = agent;
        this.problemSolved = problemSolved;
        this.ticketNumberObject = ticketNumberObject;
    }

    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public State_of_Ticket getState() {
        return state;
    }

    public void setState(State_of_Ticket state) {
        this.state = state;
    }

    public Date getDateOfCreation() {
        return dateofCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateofCreation = dateOfCreation;
    }

    public Date getDateLastUpdation() {
        return dateLastUpdate;
    }

    public void setDateLastUpdation(Date dateLastUpdation) {
        this.dateLastUpdate = dateLastUpdation;
    }

    public User getAgent() {
        return agent;
    }

    public void setAgent(User agent) {
        this.agent = agent;
    }

    public boolean isProblemSolved() {
        return problemSolved;
    }

    public void setProblemSolved(boolean problemSolved) {
        this.problemSolved = problemSolved;
    }

    public Ticket getTicketNumberObject() {
        return ticketNumberObject;
    }

    public void setTicketNumberObject(Ticket ticketNumberObject) {
        this.ticketNumberObject = ticketNumberObject;
    }

    @Override
    public TicketTrackingDTO parseToDTO() {
        int agentId = 0;
        if(agent != null){
            agentId = agent.getIdUser();
        }
        int stateId = 0;
        if(agent != null){
            stateId = state.getIdState();
        }
        return new TicketTrackingDTO(
                ticketNumber,
                stateId,
                dateofCreation,
                dateLastUpdate,
                agentId,
                problemSolved
        );
    }

    @Override
    public TicketTracking parseToEntity() {
        return this;
    }

    @Override
    public String toCSV() {
        return null;
    }
}
