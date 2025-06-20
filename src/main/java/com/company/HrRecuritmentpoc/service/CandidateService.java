package com.company.HrRecuritmentpoc.service;

import com.company.HrRecuritmentpoc.dto.request.CandidateCreateRequest;
import com.company.HrRecuritmentpoc.dto.request.CandidateUpdateRequest;
import com.company.HrRecuritmentpoc.dto.response.CandidateResponse;
import com.company.HrRecuritmentpoc.entity.Candidate;
import com.company.HrRecuritmentpoc.entity.Position;
import com.company.HrRecuritmentpoc.exception.BusinessException;
import com.company.HrRecuritmentpoc.exception.ResourceNotFoundException;
import com.company.HrRecuritmentpoc.mapper.CandidateMapper;
import com.company.HrRecuritmentpoc.repository.CandidateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class CandidateService {

    private final CandidateRepository candidateRepository;
    private final PositionService positionService;
    private final CandidateMapper candidateMapper;

    public CandidateResponse createCandidate(CandidateCreateRequest request) {
        log.info("Creating candidate with email: {}", request.getEmail());

        // Validate email uniqueness
        if (candidateRepository.existsByEmail(request.getEmail())) {
            throw new BusinessException("Candidate with email '" + request.getEmail() + "' already exists");
        }

        // Validate age
        validateAge(request.getDob());

        // Validate and fetch positions
        List<Position> positions = validateAndFetchPositions(request.getPositionIds());

        Candidate candidate = candidateMapper.toEntity(request);
        candidate.setPositions(positions);

        Candidate savedCandidate = candidateRepository.save(candidate);

        log.info("Candidate created successfully with ID: {}", savedCandidate.getId());
        return candidateMapper.toResponse(savedCandidate);
    }

    public CandidateResponse updateCandidate(Long id, CandidateUpdateRequest request) {
        log.info("Updating candidate with ID: {}", id);

        Candidate existingCandidate = candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with ID: " + id));

        // Update fields if provided
        if (StringUtils.hasText(request.getName())) {
            existingCandidate.setName(request.getName());
        }

        if (StringUtils.hasText(request.getEmail())) {
            // Validate email uniqueness for update
            if (candidateRepository.existsByEmailAndIdNot(request.getEmail(), id)) {
                throw new BusinessException("Another candidate with email '" + request.getEmail() + "' already exists");
            }
            existingCandidate.setEmail(request.getEmail());
        }

        if (request.getDob() != null) {
            validateAge(request.getDob());
            existingCandidate.setDob(request.getDob());
        }

        if (request.getPositionIds() != null && !request.getPositionIds().isEmpty()) {
            List<Position> positions = validateAndFetchPositions(request.getPositionIds());
            existingCandidate.setPositions(positions);
        }

        Candidate updatedCandidate = candidateRepository.save(existingCandidate);

        log.info("Candidate updated successfully with ID: {}", updatedCandidate.getId());
        return candidateMapper.toResponse(updatedCandidate);
    }

    @Transactional(readOnly = true)
    public CandidateResponse getCandidateById(Long id) {
        log.info("Fetching candidate with ID: {}", id);

        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with ID: " + id));

        return candidateMapper.toResponse(candidate);
    }

    @Transactional(readOnly = true)
    public List<CandidateResponse> getAllCandidates() {
        log.info("Fetching all candidates");

        List<Candidate> candidates = candidateRepository.findAll();
        return candidateMapper.toResponseList(candidates);
    }

    public void deleteCandidate(Long id) {
        log.info("Deleting candidate with ID: {}", id);

        Candidate candidate = candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with ID: " + id));

        candidateRepository.delete(candidate);
        log.info("Candidate deleted successfully with ID: {}", id);
    }

    private void validateAge(LocalDate dob) {
        if (dob == null) {
            throw new BusinessException("Date of birth is required");
        }

        int age = Period.between(dob, LocalDate.now()).getYears();
        if (age < 18) {
            throw new BusinessException("Candidate must be at least 18 years old. Current age: " + age);
        }
    }

    private List<Position> validateAndFetchPositions(List<Long> positionIds) {
        if (positionIds == null || positionIds.isEmpty()) {
            throw new BusinessException("At least one position must be selected");
        }

        List<Position> positions = positionService.findPositionsByIds(positionIds);

        if (positions.size() != positionIds.size()) {
            throw new BusinessException("One or more position IDs are invalid");
        }

        return positions;
    }
}