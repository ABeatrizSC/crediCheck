package com.abeatrizsc.proposalapp.scheduler;

import com.abeatrizsc.proposalapp.entity.Proposal;
import com.abeatrizsc.proposalapp.repository.ProposalRepository;
import com.abeatrizsc.proposalapp.service.RabbitNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class ProposalWithoutIntegration {

    private final ProposalRepository proposalRepository;

    private final RabbitNotificationService rabbitNotificationService;

    private final String exchange;

    private final Logger logger = LoggerFactory.getLogger(ProposalWithoutIntegration.class);

    public ProposalWithoutIntegration(ProposalRepository proposalRepository,
                                      RabbitNotificationService rabbitNotificationService,
                                 @Value("${rabbitmq.pendingproposal.exchange}") String exchange) {
        this.proposalRepository = proposalRepository;
        this.rabbitNotificationService = rabbitNotificationService;
        this.exchange = exchange;
    }

    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    public void findProposalWithoutIntegration() {
        proposalRepository.findAllByIntegratedIsFalse().forEach(proposal -> {
            try {
                rabbitNotificationService.notificate(proposal, exchange);
                updatedIntegratedStatus(proposal);
            } catch (RuntimeException ex) {
                logger.error(ex.getMessage());
            }
        });
    }

    private void updatedIntegratedStatus(Proposal proposal){
        proposal.setIntegrated(true);
        proposalRepository.save(proposal);
    }
}
