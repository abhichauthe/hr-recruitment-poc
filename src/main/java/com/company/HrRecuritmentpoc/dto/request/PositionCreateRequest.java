package com.company.HrRecuritmentpoc.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PositionCreateRequest {

    @NotBlank(message = "Position name is required")
    @Size(max = 50, message = "Position name must not exceed 50 characters")
    private String positionName;
}

