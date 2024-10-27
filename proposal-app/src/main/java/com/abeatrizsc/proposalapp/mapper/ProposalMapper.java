package com.abeatrizsc.proposalapp.mapper;

import com.abeatrizsc.proposalapp.dto.ProposalRequestDto;
import com.abeatrizsc.proposalapp.dto.ProposalResponseDto;
import com.abeatrizsc.proposalapp.entity.Proposal;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProposalMapper {

    ProposalMapper INSTANCE = Mappers.getMapper(ProposalMapper.class);

    @Mapping(target = "user.firstName", source = "firstName")
    @Mapping(target = "user.lastName", source = "lastName")
    @Mapping(target = "user.cpf", source = "cpf")
    @Mapping(target = "user.phone", source = "phone")
    @Mapping(target = "user.income", source = "income")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "approved", ignore = true)
    @Mapping(target = "integrated", constant = "true")
    @Mapping(target = "observation", ignore = true)
    Proposal convertDtoToProposal(ProposalRequestDto requestDto);

    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "cpf", source = "user.cpf")
    @Mapping(target = "phone", source = "user.phone")
    @Mapping(target = "income", source = "user.income")
    ProposalResponseDto convertProposalToDto(Proposal proposal);

    List<ProposalResponseDto> convertProposalListToDto(Iterable<Proposal> proposals);
}
