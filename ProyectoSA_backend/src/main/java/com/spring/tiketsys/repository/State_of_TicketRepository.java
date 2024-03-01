package com.spring.tiketsys.repository;

import com.spring.tiketsys.dto.entity.State_of_Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface State_of_TicketRepository extends JpaRepository<State_of_Ticket, Integer> {
}
