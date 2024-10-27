package com.abeatrizsc.proposalapp.repository;

import com.abeatrizsc.proposalapp.entity.Proposal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProposalRepository extends JpaRepository<Proposal, Long> {
    List<Proposal> findAllByIntegratedIsFalse();
}
