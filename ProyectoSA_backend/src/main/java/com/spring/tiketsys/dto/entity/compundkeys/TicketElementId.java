package com.spring.tiketsys.dto.entity.compundkeys;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@EqualsAndHashCode
public class TicketElementId implements Serializable {
    private int idTicketElement;
    private int ticketNumber;

}
