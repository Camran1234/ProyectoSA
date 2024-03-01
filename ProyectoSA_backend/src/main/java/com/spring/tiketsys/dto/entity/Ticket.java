package com.spring.tiketsys.dto.entity;

import jakarta.persistence.*;
import lombok.NonNull;

@Entity
@Table(name="Ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    @Column (name = "ticketNumber")
    private int ticketNumber;
    @NonNull
    @Column (name = "name")
    private String name;
    @NonNull
    @Column (name = "lastName")
    private String lastName;
    @NonNull
    @Column (name = "phone")
    private String phone;
    @NonNull
    @Column (name="description", columnDefinition = "TEXT")
    private String description;
    @NonNull
    @ManyToOne
    @JoinColumn(name = "ticketType", referencedColumnName = "idTicketProblem")
    private TicketType ticketType;
    @NonNull
    @ManyToOne
    @JoinColumn(name = "priority", referencedColumnName = "idTicketPriority")
    private TicketPriority priority;

    @NonNull
    @ManyToOne
    @JoinColumn(name="owner", referencedColumnName = "idUser")
    private User owner;

    public Ticket(@NonNull int ticketNumber, @NonNull String name, @NonNull String lastName, @NonNull String phone, @NonNull String description, @NonNull TicketType ticketType, @NonNull TicketPriority priority, @NonNull User owner) {
        this.ticketNumber = ticketNumber;
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.description = description;
        this.ticketType = ticketType;
        this.priority = priority;
        this.owner = owner;
    }

    public Ticket(){}

    public int getTicketNumber() {
        return ticketNumber;
    }

    public void setTicketNumber(int ticketNumber) {
        this.ticketNumber = ticketNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

    public TicketPriority getPriority() {
        return priority;
    }

    public void setPriority(TicketPriority priority) {
        this.priority = priority;
    }
}
