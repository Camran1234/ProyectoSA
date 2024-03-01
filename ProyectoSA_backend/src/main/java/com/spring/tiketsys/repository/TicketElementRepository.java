package com.spring.tiketsys.repository;

import com.spring.tiketsys.dto.entity.TicketElement;
import com.spring.tiketsys.dto.entity.compundkeys.TicketElementId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketElementRepository extends JpaRepository<TicketElement, TicketElementId> {
}
