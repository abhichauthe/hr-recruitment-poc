package com.company.HrRecuritmentpoc.repository;

import com.company.HrRecuritmentpoc.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PositionRepository extends JpaRepository<Position, Long> {

    Optional<Position> findByPositionName(String positionName);

    boolean existsByPositionName(String positionName);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END " +
            "FROM Candidate c JOIN c.positions p WHERE p.id = :positionId")
    boolean existsCandidateByPositionId(@Param("positionId") Long positionId);

    List<Position> findByIdIn(List<Long> ids);
}