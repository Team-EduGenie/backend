package com.edugenie.controller;

import com.edugenie.model.Unit;
import com.edugenie.repository.UnitRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/units")
@CrossOrigin(origins = "http://localhost:5173")
public class UnitController {
    private final UnitRepository unitRepository;

    public UnitController(UnitRepository unitRepository) {
        this.unitRepository = unitRepository;
    }

    @GetMapping("/subject/{subjectId}")
    public List<Unit> getUnitsBySubjectId(@PathVariable Long subjectId) {
        return unitRepository.findBySubjectId(subjectId);
    }
} 