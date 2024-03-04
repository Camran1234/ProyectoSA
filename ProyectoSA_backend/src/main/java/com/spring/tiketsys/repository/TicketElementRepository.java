package com.spring.tiketsys.repository;

import com.spring.tiketsys.dto.entity.TicketElement;
import com.spring.tiketsys.dto.entity.compundkeys.TicketElementId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TicketElementRepository extends JpaRepository<TicketElement, TicketElementId> {
    @Query(nativeQuery = true, value="SELECT COUNT(*) FROM TicketElement t WHERE t.ticketNumber = :ticketNumber")
    Optional<Integer> findMaxIdForTicketElement(@Param("ticketNumber") int ticketNumber);
    @Query(nativeQuery = true, value =
            "SELECT te.url" +
                    "            FROM TicketElement te" +
                    "            WHERE" +
                    "                te.ticketNumber= :ticketNumber")
    List<String> getElements(int ticketNumber);

}
