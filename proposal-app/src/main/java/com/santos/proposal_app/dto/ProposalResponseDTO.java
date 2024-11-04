package com.santos.proposal_app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProposalResponseDTO {

    private Long id;

    private String name;

    private String lastName;

    private String phone;

    private String cpf;

    private Double income;

    private String valueRequestFmt;

    private int paymentTime;

    private Boolean approved;

    private String observation;
}
