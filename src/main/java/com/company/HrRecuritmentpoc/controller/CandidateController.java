package com.company.HrRecuritmentpoc.controller;

import com.company.HrRecuritmentpoc.dto.common.ApiResponse;
import com.company.HrRecuritmentpoc.dto.request.CandidateCreateRequest;
import com.company.HrRecuritmentpoc.dto.request.CandidateUpdateRequest;
import com.company.HrRecuritmentpoc.dto.response.CandidateResponse;
import com.company.HrRecuritmentpoc.service.CandidateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidates")
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateService candidateService;

    @PostMapping
    public ResponseEntity<ApiResponse<CandidateResponse>> createCandidate(
            @Valid @RequestBody CandidateCreateRequest request) {
        CandidateResponse response = candidateService.createCandidate(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Candidate created successfully", response));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<CandidateResponse>> updateCandidate(
            @PathVariable Long id,
            @Valid @RequestBody CandidateUpdateRequest request) {
        CandidateResponse response = candidateService.updateCandidate(id, request);
        return ResponseEntity.ok(ApiResponse.success("Candidate updated successfully", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CandidateResponse>> getCandidateById(@PathVariable Long id) {
        CandidateResponse response = candidateService.getCandidateById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CandidateResponse>>> getAllCandidates() {
        List<CandidateResponse> responses = candidateService.getAllCandidates();
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCandidate(@PathVariable Long id) {
        candidateService.deleteCandidate(id);
        return ResponseEntity.ok(ApiResponse.success("Candidate deleted successfully", null));
    }
}