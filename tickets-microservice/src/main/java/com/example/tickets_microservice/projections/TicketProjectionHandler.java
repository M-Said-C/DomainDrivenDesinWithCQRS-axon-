package com.example.tickets_microservice.projections;


import com.example.tickets_microservice.events.TicketCreatedEvent;
import com.example.tickets_microservice.repos.TicketRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class TicketProjectionHandler {

    private final TicketRepository ticketRepository;

    public TicketProjectionHandler(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @EventHandler
    public void on(TicketCreatedEvent event) {
        TicketProjection ticket = new TicketProjection();
        ticket.setCodeTicket(event.getCodeTicket());
        ticket.setPrixTicket(event.getPrixTicket());
        ticket.setTypeTicket(event.getTypeTicket());
        ticket.setEvenementId(event.getEvenementId());
        ticket.setInternauteId(event.getInternauteId());
        ticketRepository.save(ticket);
    }
}
