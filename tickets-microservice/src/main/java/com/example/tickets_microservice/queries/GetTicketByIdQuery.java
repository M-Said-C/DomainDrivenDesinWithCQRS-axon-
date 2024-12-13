package com.example.tickets_microservice.queries;


public class GetTicketByIdQuery {
    private final String codeTicket;

    public GetTicketByIdQuery(String codeTicket) {
        this.codeTicket = codeTicket;
    }

    public String getCodeTicket() {
        return codeTicket;
    }
}
