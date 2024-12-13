package com.example.tickets_microservice.queries;


import com.example.tickets_microservice.projections.TicketProjection;
import com.example.tickets_microservice.repos.TicketRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TicketQueryHandler {

    private final TicketRepository ticketRepository;

    public TicketQueryHandler(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @QueryHandler
    public List<TicketProjection> handle(GetAllTicketsQuery query) {
        return ticketRepository.findAll();
    }
}
