package com.abeatrizsc.proposalapp.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "proposals")
@Data
public class Proposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double requestedAmount;
    private int paymentTerm;
    private Boolean approved;
    private boolean integrated;
    private String observation;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "id_user")
    @JsonManagedReference //evita recursividade
    private User user;
}
