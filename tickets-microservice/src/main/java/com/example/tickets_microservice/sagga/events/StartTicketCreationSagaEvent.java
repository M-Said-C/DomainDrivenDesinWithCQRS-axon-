package com.example.tickets_microservice.sagga.events;


public class StartTicketCreationSagaEvent {

    private final String codeTicket;
    private final Double prixTicket;
    private final String typeTicket;
    private final Long evenementId;
    private final Long internauteId;

    public StartTicketCreationSagaEvent(String codeTicket, Double prixTicket, String typeTicket, Long evenementId, Long internauteId) {
        this.codeTicket = codeTicket;
        this.prixTicket = prixTicket;
        this.typeTicket = typeTicket;
        this.evenementId = evenementId;
        this.internauteId = internauteId;
    }

    // Getters
    public String getCodeTicket() {
        return codeTicket;
    }

    public Double getPrixTicket() {
        return prixTicket;
    }

    public String getTypeTicket() {
        return typeTicket;
    }

    public Long getEvenementId() {
        return evenementId;
    }

    public Long getInternauteId() {
        return internauteId;
    }
}
