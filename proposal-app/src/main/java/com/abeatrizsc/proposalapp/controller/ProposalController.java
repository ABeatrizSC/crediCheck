package com.abeatrizsc.proposalapp.controller;

import com.abeatrizsc.proposalapp.dto.ProposalRequestDto;
import com.abeatrizsc.proposalapp.dto.ProposalResponseDto;
import com.abeatrizsc.proposalapp.entity.Proposal;
import com.abeatrizsc.proposalapp.service.ProposalService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/api/proposals")
public class ProposalController {
    private ProposalService proposalService;

    @GetMapping
    public ResponseEntity<List<ProposalResponseDto>> findAll(){
        return ResponseEntity.ok(proposalService.findAll());
    }

    @PostMapping
    public ResponseEntity<ProposalResponseDto> create(@RequestBody ProposalRequestDto requestDto){
        ProposalResponseDto response = proposalService.create(requestDto);
        return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id")
                .buildAndExpand(response.getId())
                .toUri()).body(response);
    }
}
