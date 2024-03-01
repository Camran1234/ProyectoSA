package com.spring.tiketsys.dto.model;

public class TicketDTO {
    private int ticketNumber;
    private String name;
    private String lastName;
    private String phone;
    private String description;
    private int ticketType;
    private int priority;
    private int owner;

    public TicketDTO() {
    }

    public TicketDTO(int ticketNumber, String name, String lastName, String phone, String description, int ticketType, int priority, int owner) {
        this.ticketNumber = ticketNumber;
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.description = description;
        this.ticketType = ticketType;
        this.priority = priority;
        this.owner = owner;
    }

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

    public int getTicketType() {
        return ticketType;
    }

    public void setTicketType(int ticketType) {
        this.ticketType = ticketType;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }
}
