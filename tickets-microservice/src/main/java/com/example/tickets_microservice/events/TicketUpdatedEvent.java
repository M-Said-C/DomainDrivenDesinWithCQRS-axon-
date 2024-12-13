package com.example.tickets_microservice.events;


public class TicketUpdatedEvent {
    private final String codeTicket;
    private final Double prixTicket;
    private final String typeTicket;

    public TicketUpdatedEvent(String codeTicket, Double prixTicket, String typeTicket) {
        this.codeTicket = codeTicket;
        this.prixTicket = prixTicket;
        this.typeTicket = typeTicket;
    }

    public String getCodeTicket() {
        return codeTicket;
    }

    public Double getPrixTicket() {
        return prixTicket;
    }

    public String getTypeTicket() {
        return typeTicket;
    }
}
