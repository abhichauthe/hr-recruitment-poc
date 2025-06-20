package com.company.HrRecuritmentpoc.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "positions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "position_name", length = 50, unique = true, nullable = false)
    @NotBlank(message = "Position name is required")
    @Size(max = 50, message = "Position name must not exceed 50 characters")
    private String positionName;

    @ManyToMany(mappedBy = "positions", fetch = FetchType.LAZY)
    private List<Candidate> candidates;

    // Constructor for creation
    public Position(String positionName) {
        this.positionName = positionName;
    }
}