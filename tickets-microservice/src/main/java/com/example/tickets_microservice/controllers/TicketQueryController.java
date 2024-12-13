package com.example.tickets_microservice.controllers;

import com.example.tickets_microservice.projections.TicketProjection;
import com.example.tickets_microservice.queries.GetAllTicketsQuery;
import com.example.tickets_microservice.queries.GetTicketByIdQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/tickets")
public class TicketQueryController {

    private final QueryGateway queryGateway;

    public TicketQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping
    public CompletableFuture<List<TicketProjection>> getAllTickets() {
        return queryGateway.query(
                new GetAllTicketsQuery(),
                ResponseTypes.multipleInstancesOf(TicketProjection.class)
        );
    }

    @GetMapping("/{codeTicket}")
    public CompletableFuture<TicketProjection> getTicketById(@PathVariable String codeTicket) {
        return queryGateway.query(new GetTicketByIdQuery(codeTicket), TicketProjection.class);
    }
}
