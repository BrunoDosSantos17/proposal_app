package com.santos.proposal_app.repository;

import com.santos.proposal_app.entity.Proposal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProposalRepository extends CrudRepository<Proposal, Long> {
    List<Proposal> findAllByIntegratedIsFalse();
}
