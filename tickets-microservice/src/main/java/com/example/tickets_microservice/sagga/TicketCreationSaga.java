package com.example.tickets_microservice.sagga;


import com.example.tickets_microservice.commands.*;
import com.example.tickets_microservice.events.*;
import com.example.tickets_microservice.sagga.commands.CancelReservationCommand;
import com.example.tickets_microservice.sagga.commands.CreateTicketCommand;
import com.example.tickets_microservice.sagga.commands.ReservePlacesCommand;
import com.example.tickets_microservice.sagga.commands.validations.ValidateEventCommand;
import com.example.tickets_microservice.sagga.commands.validations.ValidateInternauteCommand;
import com.example.tickets_microservice.sagga.events.*;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.spring.stereotype.Saga;


@Saga
@Slf4j
public class TicketCreationSaga {

    @Inject
    private transient CommandGateway commandGateway;

    private String codeTicket;
    private Long evenementId;
    private Long internauteId;

    @StartSaga
    @SagaEventHandler(associationProperty = "codeTicket")
    public void handle(StartTicketCreationSagaEvent event) {
        log.info("Starting saga for ticket creation: {}", event.getCodeTicket());

        this.codeTicket = event.getCodeTicket();
        this.evenementId = event.getEvenementId();
        this.internauteId = event.getInternauteId();

        // Step 1: Validate Internaute
        commandGateway.send(new ValidateInternauteCommand(internauteId, codeTicket));
    }

    @SagaEventHandler(associationProperty = "codeTicket")
    public void handle(InternauteValidatedEvent event) {
        log.info("Internaute validated: {}", event.getInternauteId());

        // Step 2: Validate Event
        commandGateway.send(new ValidateEventCommand(evenementId, codeTicket));
    }

    @SagaEventHandler(associationProperty = "codeTicket")
    public void handle(EventValidatedEvent event) {
        log.info("Event validated: {}", event.getEvenementId());

        // Step 3: Reserve Places
        commandGateway.send(new ReservePlacesCommand(evenementId, 1, codeTicket));
    }

    @SagaEventHandler(associationProperty = "codeTicket")
    public void handle(PlacesReservedEvent event) {
        log.info("Places reserved for event: {}", event.getEvenementId());

        // Step 4: Create Ticket
        commandGateway.send(new CreateTicketCommand(
                codeTicket,
                event.getPrixTicket(),
                event.getTypeTicket(),
                evenementId,
                internauteId
        ));
    }

    @SagaEventHandler(associationProperty = "codeTicket")
    @EndSaga
    public void handle(TicketCreatedEvent event) {
        log.info("Ticket created successfully: {}", event.getCodeTicket());
    }

    // Handle failure scenarios
    @SagaEventHandler(associationProperty = "codeTicket")
    public void handle(ValidationFailedEvent event) {
        log.error("Validation failed: {}", event.getReason());

        // Rollback: Emit compensating events
        commandGateway.send(new CancelReservationCommand(evenementId, 1, codeTicket));
        commandGateway.send(new CancelTicketCommand(codeTicket));

        // End the saga after rollback
        end();
    }

    private void end() {
        log.info("Saga ended for ticket: {}", codeTicket);
    }
}
