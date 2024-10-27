package com.abeatrizsc.proposalapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProposalResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String cpf;
    private String phone;
    private Double income;
    private Double requestedAmount;
    private int paymentTerm;
    private Boolean approved;
    private String observation;
}
