package com.example.tickets_microservice.aggregate;

import com.example.tickets_microservice.command.CreateTicketCommand;
import com.example.tickets_microservice.commands.UpdateTicketCommand;
import com.example.tickets_microservice.commands.CancelTicketCommand;
import com.example.tickets_microservice.events.TicketCreatedEvent;
import com.example.tickets_microservice.events.TicketUpdatedEvent;
import com.example.tickets_microservice.events.TicketCancelledEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Aggregate
@NoArgsConstructor
public class TicketAggregate {

    @AggregateIdentifier
    private String codeTicket;
    private Double prixTicket;
    private String typeTicket;
    private Long evenementId;
    private Long internauteId;

    @CommandHandler
    public TicketAggregate(CreateTicketCommand command) {
        if (command.getPrixTicket() <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero.");
        }
        apply(new TicketCreatedEvent(
                command.getCodeTicket(),
                command.getPrixTicket(),
                command.getTypeTicket(),
                command.getEvenementId(),
                command.getInternauteId()
        ));
    }

    @EventSourcingHandler
    public void on(TicketCreatedEvent event) {
        this.codeTicket = event.getCodeTicket();
        this.prixTicket = event.getPrixTicket();
        this.typeTicket = event.getTypeTicket();
        this.evenementId = event.getEvenementId();
        this.internauteId = event.getInternauteId();
    }

    @CommandHandler
    public void handle(UpdateTicketCommand command) {
        if (command.getPrixTicket() <= 0) {
            throw new IllegalArgumentException("Price must be greater than zero.");
        }
        apply(new TicketUpdatedEvent(
                this.codeTicket,
                command.getPrixTicket(),
                command.getTypeTicket()
        ));
    }

    @EventSourcingHandler
    public void on(TicketUpdatedEvent event) {
        this.prixTicket = event.getPrixTicket();
        this.typeTicket = event.getTypeTicket();
    }

    @CommandHandler
    public void handle(CancelTicketCommand command) {
        apply(new TicketCancelledEvent(this.codeTicket));
    }

    @EventSourcingHandler
    public void on(TicketCancelledEvent event) {
        // Handle deletion logic if needed
    }
}
