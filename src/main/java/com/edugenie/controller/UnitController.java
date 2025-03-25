package com.edugenie.controller;

import com.edugenie.model.Unit;
import com.edugenie.repository.UnitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/units")
@RequiredArgsConstructor
public class UnitController {

    private final UnitRepository unitRepository;

    @GetMapping("/subject/{subjectId}")
    public List<Unit> getUnitsBySubjectId(@PathVariable Long subjectId) {
        return unitRepository.findBySubjectId(subjectId);
    }
} 