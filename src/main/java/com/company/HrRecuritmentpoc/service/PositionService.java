package com.company.HrRecuritmentpoc.service;

import com.company.HrRecuritmentpoc.dto.request.PositionCreateRequest;
import com.company.HrRecuritmentpoc.dto.response.PositionResponse;
import com.company.HrRecuritmentpoc.entity.Position;
import com.company.HrRecuritmentpoc.exception.BusinessException;
import com.company.HrRecuritmentpoc.exception.ResourceNotFoundException;
import com.company.HrRecuritmentpoc.mapper.PositionMapper;
import com.company.HrRecuritmentpoc.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PositionService {

    private final PositionRepository positionRepository;
    private final PositionMapper positionMapper;

    public PositionResponse createPosition(PositionCreateRequest request) {
        log.info("Creating position with name: {}", request.getPositionName());

        // Check if position name already exists
        if (positionRepository.existsByPositionName(request.getPositionName())) {
            throw new BusinessException("Position with name '" + request.getPositionName() + "' already exists");
        }

        Position position = positionMapper.toEntity(request);
        Position savedPosition = positionRepository.save(position);

        log.info("Position created successfully with ID: {}", savedPosition.getId());
        return positionMapper.toResponse(savedPosition);
    }

    @Transactional(readOnly = true)
    public PositionResponse getPositionById(Long id) {
        log.info("Fetching position with ID: {}", id);

        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Position not found with ID: " + id));

        return positionMapper.toResponse(position);
    }

    @Transactional(readOnly = true)
    public List<PositionResponse> getAllPositions() {
        log.info("Fetching all positions");

        List<Position> positions = positionRepository.findAll();
        return positionMapper.toResponseList(positions);
    }

    public void deletePosition(Long id) {
        log.info("Attempting to delete position with ID: {}", id);

        // Check if position exists
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Position not found with ID: " + id));

        // Check if position is linked to any candidates
        if (positionRepository.existsCandidateByPositionId(id)) {
            throw new BusinessException("Cannot delete position as it is linked to one or more candidates");
        }

        positionRepository.delete(position);
        log.info("Position deleted successfully with ID: {}", id);
    }

    @Transactional(readOnly = true)
    public List<Position> findPositionsByIds(List<Long> positionIds) {
        return positionRepository.findByIdIn(positionIds);
    }
}