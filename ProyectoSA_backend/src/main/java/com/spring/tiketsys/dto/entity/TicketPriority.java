package com.spring.tiketsys.dto.entity;

import jakarta.persistence.*;
import lombok.NonNull;

@Entity
@Table(name="TicketPriority")
public class TicketPriority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "idTicketPriority")
    private int idTicketPriority;
    @NonNull
    private String priority;

    public TicketPriority(@NonNull int idTicketPriority, @NonNull String priority) {
        this.idTicketPriority = idTicketPriority;
        this.priority = priority;
    }

    public TicketPriority() {
    }

    public int getIdTicketPriority() {
        return idTicketPriority;
    }

    public void setIdTicketPriority(int idTicketPriority) {
        this.idTicketPriority = idTicketPriority;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }
}
