package com.edugenie.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiagnosisResultDto {

    private String feedback;
    private List<String> weakConcepts;
    private String similarProblem;
}
