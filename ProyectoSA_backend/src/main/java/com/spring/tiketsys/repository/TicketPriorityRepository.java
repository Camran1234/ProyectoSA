package com.spring.tiketsys.repository;

import com.spring.tiketsys.dto.entity.TicketPriority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketPriorityRepository extends JpaRepository<TicketPriority, Integer> {
}
