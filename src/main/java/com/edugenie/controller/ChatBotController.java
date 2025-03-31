package com.edugenie.controller;

import org.hibernate.query.results.complete.CompleteFetchBuilderEmbeddableValuedModelPart;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edugenie.dto.DiagnosisResultDto;
import com.edugenie.dto.FeedbackRequestDto;
import com.edugenie.service.ChatBotService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/chatbot")
@RequiredArgsConstructor
public class ChatBotController {

    private final ChatBotService chatBotService;

    @PostMapping("/diagnosis-and-recommendation")
    public ResponseEntity<DiagnosisResultDto> diagnoseAndRecommend(@RequestBody FeedbackRequestDto dto) {
        return ResponseEntity.ok(chatBotService.diagnoseAndRecommend(dto));
    }
}
