package com.santos.proposal_app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProposalRequestDTO {

    private String name;
    private String lastName;
    private String phone;
    private String cpf;
    private Double income;
    private Double valueRequest;

    private int paymentTime;
}
