package com.example.tickets_microservice.repos;


import com.example.tickets_microservice.projections.TicketProjection;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<TicketProjection, String> {
}
