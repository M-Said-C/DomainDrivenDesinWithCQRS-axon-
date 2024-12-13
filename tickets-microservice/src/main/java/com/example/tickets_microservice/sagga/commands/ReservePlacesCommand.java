package com.example.tickets_microservice.sagga.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

public class ReservePlacesCommand {
    @TargetAggregateIdentifier
    private final Long evenementId;
    private final int placesToReserve;
    private final String codeTicket;

    public ReservePlacesCommand(Long evenementId, int placesToReserve, String codeTicket) {
        this.evenementId = evenementId;
        this.placesToReserve = placesToReserve;
        this.codeTicket = codeTicket;
    }

    public Long getEvenementId() {
        return evenementId;
    }

    public int getPlacesToReserve() {
        return placesToReserve;
    }

    public String getCodeTicket() {
        return codeTicket;
    }
}
