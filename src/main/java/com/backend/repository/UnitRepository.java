package com.backend.repository;

import com.backend.model.Subject;
import com.backend.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
    @Query("SELECT u FROM Unit u WHERE u.subject.subjectId = :subjectId")
    List<Unit> findBySubjectId(@Param("subjectId") Long subjectId);

    Optional<Unit> findByUnitNameAndSubject(String unitName, Subject subject);
} 