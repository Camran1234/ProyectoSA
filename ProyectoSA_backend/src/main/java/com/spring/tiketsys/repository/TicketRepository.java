package com.spring.tiketsys.repository;

import com.spring.tiketsys.dto.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
}
