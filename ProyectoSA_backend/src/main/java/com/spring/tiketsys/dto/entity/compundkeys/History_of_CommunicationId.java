package com.spring.tiketsys.dto.entity.compundkeys;

import com.spring.tiketsys.dto.entity.TicketTracking;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


@Setter
@Getter
@EqualsAndHashCode
public class History_of_CommunicationId implements Serializable {
    private int idHistory;
    private int ticketNumber;



}
