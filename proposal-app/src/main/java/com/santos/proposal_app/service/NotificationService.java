package com.santos.proposal_app.service;

import com.santos.proposal_app.dto.ProposalResponseDTO;
import com.santos.proposal_app.entity.Proposal;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private RabbitTemplate rabbitTemplate;

    public void notification(Proposal proposal, String exchange) {
        rabbitTemplate.convertAndSend(exchange, "", proposal);
    }
}
