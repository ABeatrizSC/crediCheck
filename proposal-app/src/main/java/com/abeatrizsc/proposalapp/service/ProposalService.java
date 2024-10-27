package com.abeatrizsc.proposalapp.service;

import com.abeatrizsc.proposalapp.dto.ProposalRequestDto;
import com.abeatrizsc.proposalapp.dto.ProposalResponseDto;
import com.abeatrizsc.proposalapp.entity.Proposal;
import com.abeatrizsc.proposalapp.mapper.ProposalMapper;
import com.abeatrizsc.proposalapp.repository.ProposalRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProposalService {

    private String exchange;
    private ProposalRepository proposalRepository;
    private RabbitNotificationService rabbitNotificationService;

    public ProposalService(@Value("${rabbitmq.pendingproposal.exchange}") String exchange, RabbitNotificationService rabbitNotificationService, ProposalRepository proposalRepository) {
        this.exchange = exchange;
        this.rabbitNotificationService = rabbitNotificationService;
        this.proposalRepository = proposalRepository;
    }

    public ProposalResponseDto create(ProposalRequestDto requestDto){
        Proposal proposal = ProposalMapper.INSTANCE.convertDtoToProposal(requestDto);
        proposalRepository.save(proposal);

        notificateRabbitMQ(proposal);

        return ProposalMapper.INSTANCE.convertProposalToDto(proposal);
    }

    private void notificateRabbitMQ(Proposal proposal){
        try{
            rabbitNotificationService.notificate(proposal, exchange);
        } catch (RuntimeException e){
            proposal.setIntegrated(false);
            proposalRepository.save(proposal);
        }
    }

    public List<ProposalResponseDto> findAll(){
        return ProposalMapper.INSTANCE.convertProposalListToDto(proposalRepository.findAll());
    }
}
