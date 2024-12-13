package com.example.tickets_microservice.sagga.commands;


import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class CancelReservationCommand {

    @TargetAggregateIdentifier
    private final Long evenementId;
    private final int placesToRestore;
    private final String codeTicket;

    public CancelReservationCommand(Long evenementId, int placesToRestore, String codeTicket) {
        this.evenementId = evenementId;
        this.placesToRestore = placesToRestore;
        this.codeTicket = codeTicket;
    }

    // Getters
    public Long getEvenementId() {
        return evenementId;
    }

    public int getPlacesToRestore() {
        return placesToRestore;
    }

    public String getCodeTicket() {
        return codeTicket;
    }
}
