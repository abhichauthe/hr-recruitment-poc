package com.company.HrRecuritmentpoc.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CandidateUpdateRequest {

    @Size(max = 50, message = "Name must not exceed 50 characters")
    private String name;

    @Email(message = "Email must be valid")
    private String email;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dob;

    private List<Long> positionIds;
}