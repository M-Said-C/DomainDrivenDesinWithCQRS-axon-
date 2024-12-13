package com.example.tickets_microservice.events;


public class TicketCancelledEvent {
    private final String codeTicket;

    public TicketCancelledEvent(String codeTicket) {
        this.codeTicket = codeTicket;
    }

    public String getCodeTicket() {
        return codeTicket;
    }
}
