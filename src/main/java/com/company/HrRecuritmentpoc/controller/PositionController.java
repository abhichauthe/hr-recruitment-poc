package com.company.HrRecuritmentpoc.controller;

import com.company.HrRecuritmentpoc.dto.common.ApiResponse;
import com.company.HrRecuritmentpoc.dto.request.PositionCreateRequest;
import com.company.HrRecuritmentpoc.dto.response.PositionResponse;
import com.company.HrRecuritmentpoc.service.PositionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/positions")
@RequiredArgsConstructor
public class PositionController {

    private final PositionService positionService;

    @PostMapping
    public ResponseEntity<ApiResponse<PositionResponse>> createPosition(
            @Valid @RequestBody PositionCreateRequest request) {
        PositionResponse response = positionService.createPosition(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Position created successfully", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PositionResponse>> getPositionById(@PathVariable Long id) {
        PositionResponse response = positionService.getPositionById(id);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PositionResponse>>> getAllPositions() {
        List<PositionResponse> responses = positionService.getAllPositions();
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePosition(@PathVariable Long id) {
        positionService.deletePosition(id);
        return ResponseEntity.ok(ApiResponse.success("Position deleted successfully", null));
    }
}