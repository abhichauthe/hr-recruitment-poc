package com.company.HrRecuritmentpoc.mapper;


import com.company.HrRecuritmentpoc.dto.request.PositionCreateRequest;
import com.company.HrRecuritmentpoc.dto.response.PositionResponse;
import com.company.HrRecuritmentpoc.entity.Position;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PositionMapper {

    public Position toEntity(PositionCreateRequest request) {
        return new Position(request.getPositionName());
    }

    public PositionResponse toResponse(Position position) {
        return new PositionResponse(position.getId(), position.getPositionName());
    }

    public List<PositionResponse> toResponseList(List<Position> positions) {
        return positions.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
