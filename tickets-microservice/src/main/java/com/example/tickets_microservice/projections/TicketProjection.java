package com.example.tickets_microservice.projections;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
public class TicketProjection {
    @Id
    private String codeTicket;
    private Double prixTicket;
    private String typeTicket;
    private Long evenementId;
    private Long internauteId;

    public String getCodeTicket() {
        return codeTicket;
    }

    public void setCodeTicket(String codeTicket) {
        this.codeTicket = codeTicket;
    }

    public Double getPrixTicket() {
        return prixTicket;
    }

    public void setPrixTicket(Double prixTicket) {
        this.prixTicket = prixTicket;
    }

    public String getTypeTicket() {
        return typeTicket;
    }

    public void setTypeTicket(String typeTicket) {
        this.typeTicket = typeTicket;
    }

    public Long getEvenementId() {
        return evenementId;
    }

    public void setEvenementId(Long evenementId) {
        this.evenementId = evenementId;
    }

    public Long getInternauteId() {
        return internauteId;
    }

    public void setInternauteId(Long internauteId) {
        this.internauteId = internauteId;
    }
}
