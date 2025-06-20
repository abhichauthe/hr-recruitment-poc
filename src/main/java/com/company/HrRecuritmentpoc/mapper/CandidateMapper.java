package com.company.HrRecuritmentpoc.mapper;

import com.company.HrRecuritmentpoc.dto.request.CandidateCreateRequest;
import com.company.HrRecuritmentpoc.dto.response.CandidateResponse;
import com.company.HrRecuritmentpoc.entity.Candidate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CandidateMapper {

    private final PositionMapper positionMapper;

    public Candidate toEntity(CandidateCreateRequest request) {
        Candidate candidate = new Candidate();
        candidate.setName(request.getName());
        candidate.setEmail(request.getEmail());
        candidate.setDob(request.getDob());
        return candidate;
    }

    public CandidateResponse toResponse(Candidate candidate) {
        CandidateResponse response = new CandidateResponse();
        response.setId(candidate.getId());
        response.setName(candidate.getName());
        response.setEmail(candidate.getEmail());
        response.setDob(candidate.getDob());
        response.setAge(candidate.getAge());

        if (candidate.getPositions() != null) {
            response.setPositions(positionMapper.toResponseList(candidate.getPositions()));
        }

        return response;
    }

    public List<CandidateResponse> toResponseList(List<Candidate> candidates) {
        return candidates.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}