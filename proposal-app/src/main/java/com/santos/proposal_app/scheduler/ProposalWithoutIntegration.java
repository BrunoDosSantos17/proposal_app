package com.santos.proposal_app.scheduler;

import com.santos.proposal_app.entity.Proposal;
import com.santos.proposal_app.repository.ProposalRepository;
import com.santos.proposal_app.service.NotificationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class ProposalWithoutIntegration {
    private final ProposalRepository proposalRepository;

    private final NotificationService notificationService;

    private final String exchange;

    public ProposalWithoutIntegration(ProposalRepository proposalRepository,
                                      NotificationService notificationService,
                                      @Value("${rabbitmq.proposalpeding.exchange}") String exchange) {
        this.proposalRepository = proposalRepository;
        this.notificationService = notificationService;
        this.exchange = exchange;
    }

    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    public void searchProposalWithoutIntegration() {
        proposalRepository.findAllByIntegratedIsFalse().forEach(proposal -> {
            try {
                notificationService.notification(proposal, exchange);
                this.updateProposal(proposal);
            }catch (RuntimeException ex) {
                System.out.println(ex);
            }
        });
    }

    public void updateProposal(Proposal proposal) {
        proposal.setIntegrated(true);
        proposalRepository.save(proposal);
    }
}
