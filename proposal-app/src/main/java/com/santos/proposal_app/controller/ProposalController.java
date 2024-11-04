package com.santos.proposal_app.controller;

import com.santos.proposal_app.service.ProposalService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import com.santos.proposal_app.dto.ProposalRequestDTO;
import com.santos.proposal_app.dto.ProposalResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/proposal")
@AllArgsConstructor
public class ProposalController {

    ProposalService proposalService;
    @PostMapping
    public ResponseEntity<ProposalResponseDTO> create(@RequestBody ProposalRequestDTO requestDto) {

        ProposalResponseDTO response = proposalService.create(requestDto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ProposalResponseDTO>> getProposal() {
        return ResponseEntity.ok(proposalService.getProposal());
    }
}
