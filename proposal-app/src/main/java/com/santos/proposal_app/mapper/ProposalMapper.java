package com.santos.proposal_app.mapper;

import com.santos.proposal_app.dto.ProposalRequestDTO;
import com.santos.proposal_app.dto.ProposalResponseDTO;
import com.santos.proposal_app.entity.Proposal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.text.NumberFormat;
import java.util.List;

@Mapper
public interface ProposalMapper {

    ProposalMapper INSTANCE = Mappers.getMapper(ProposalMapper.class);
    @Mapping(target = "user.name", source = "name")
    @Mapping(target = "user.lastName", source = "lastName")
    @Mapping(target = "user.cpf", source = "cpf")
    @Mapping(target = "user.phone", source = "phone")
    @Mapping(target = "user.income", source = "income")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "approved", ignore = true)
    @Mapping(target = "integrated", constant = "true")
    @Mapping(target = "observation", ignore = true)
    Proposal converDtoToProposal(ProposalRequestDTO proposalRequestDTO);

    @Mapping(target = "name", source = "user.name")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "cpf", source = "user.cpf")
    @Mapping(target = "phone", source = "user.phone")
    @Mapping(target = "income", source = "user.income")
    @Mapping(target = "valueRequestFmt", expression = "java(setValueRequestFmt(proposal))")
    ProposalResponseDTO convertEntityToDto(Proposal proposal);

    List<ProposalResponseDTO> convertListEntityToListDto(Iterable<Proposal> proposals);

    default String setValueRequestFmt(Proposal proposal) {
        return NumberFormat.getCurrencyInstance().format(proposal.getValueRequest());
    }
}
