package com.santos.proposal_app.service;

import com.santos.proposal_app.dto.ProposalRequestDTO;
import com.santos.proposal_app.dto.ProposalResponseDTO;
import com.santos.proposal_app.entity.Proposal;
import com.santos.proposal_app.mapper.ProposalMapper;
import com.santos.proposal_app.repository.ProposalRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProposalService {


    final private ProposalRepository proposalRepository;

    final private NotificationService notificationService;

    final private String exchange;

    public ProposalService(ProposalRepository proposalRepository,
                           NotificationService notificationService,
                           @Value("${rabbitmq.proposalpeding.exchange}") String exchange) {
        this.proposalRepository = proposalRepository;
        this.notificationService = notificationService;
        this.exchange = exchange;
    }

    public ProposalResponseDTO create(ProposalRequestDTO requestDTO) {
        Proposal proposal = ProposalMapper.INSTANCE.converDtoToProposal(requestDTO);
        proposalRepository.save(proposal);
        this.notificationRabbitMq(proposal);
        return ProposalMapper.INSTANCE.convertEntityToDto(proposal);
    }

    public void notificationRabbitMq(Proposal proposal ){
        try {
            notificationService.notification(proposal, this.exchange);
        }catch (RuntimeException ex) {
            proposal.setIntegrated(false);
            this.proposalRepository.save(proposal);
        }

    }

    public List<ProposalResponseDTO> getProposal() {
        return ProposalMapper.INSTANCE.convertListEntityToListDto(proposalRepository.findAll());
    }
}
