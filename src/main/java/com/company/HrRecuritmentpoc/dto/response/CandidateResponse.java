package com.company.HrRecuritmentpoc.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateResponse {
    private Long id;
    private String name;
    private String email;
    private LocalDate dob;
    private int age;
    private List<PositionResponse> positions;
}