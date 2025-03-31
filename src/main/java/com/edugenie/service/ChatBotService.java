package com.edugenie.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.edugenie.dto.DiagnosisResultDto;
import com.edugenie.dto.FeedbackRequestDto;
import com.edugenie.model.ChatLog;
import com.edugenie.repository.ChatLogRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatBotService {
    
    private final OpenAiService openAiService;
    private final ChatLogRepository chatLogRepository;

    public DiagnosisResultDto diagnoseAndRecommend(FeedbackRequestDto dto) {
        // 1. 오답 피드백 생성
        String feedbackPrompt = """
            문제: "%s"
            사용자의 답: "%s"
            정답: "%s"

            이 문제에서 왜 틀렸는지 설명해줘.
            관련 개념을 쉽게 설명하고, 예시를 들어줘.
            """.formatted(dto.getQuestion(), dto.getUserAnswer(), dto.getCorrectAnswer());

        String feedback = openAiService.chatWithGPT(feedbackPrompt);

        // 로그 저장
        chatLogRepository.save(new ChatLog(dto.getStudentId(), "USER", "답변: " + dto.getUserAnswer()));
        chatLogRepository.save(new ChatLog(dto.getStudentId(), "AI", feedback));

        // 2. 취약 개념 추출
        List<ChatLog> logs = chatLogRepository.findByStudentIdOrderByTimestampDesc(dto.getStudentId());
        StringBuilder history = new StringBuilder();
        for (ChatLog log : logs) {
            if ("AI".equals(log.getRole())) {
                history.append(log.getMessage()).append("\n");
            }
        }

        String conceptPrompt = """
            다음은 학생의 오답 피드백 기록입니다.
            여기서 자주 언급된 개념을 3개 이하로 추출해서 리스트로 정리해줘.

            %s
            """.formatted(history.toString());

        String conceptListStr = openAiService.chatWithGPT(conceptPrompt);

        List<String> weakConcepts = Arrays.stream(conceptListStr.split("\n"))
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .limit(3)
            .collect(Collectors.toList());

        // 3. 유사 개념 문제 생성 (가장 첫 번째 개념 기준)
        String selectedConcept = weakConcepts.isEmpty() ? "기초 개념" : weakConcepts.get(0);

        String problemPrompt = """
        당신은 학생 맞춤형 학습 도우미입니다.
        학생이 방금 풀었던 문제와 관련된 개념을 연습할 수 있는 유사 문제를 만들어주세요.

        원래 문제: "%s"
        강조할 개념: "%s"

        이 개념을 초급 수준에서 다시 연습할 수 있도록,
        객관식 문제 1개와 보기 4개, 정답을 포함해서 출력해 주세요.
        문제는 원래 문제와 유사한 주제를 다뤄야 합니다.
        """.formatted(dto.getQuestion(), selectedConcept);

        String similarProblem = openAiService.chatWithGPT(problemPrompt);

        return new DiagnosisResultDto(feedback, weakConcepts, similarProblem);
    }

     /**
     * 브랜치 만드는법 
     * git checkout -b dev-원하는브랜치이름
     * 
     * 커밋버튼 누르기
     * 
     * git push origin dev-원하는브랜치이름
     * 
     * - 끝 -
     * 
     * 근데여기서 우리는 이미 테스트때문에 한 커밋에 이미 푸시를 함
     * 
     * commit 안하고 add만 함
     * 
     * git commit --amend --no-edit
     * 
     * git push -f origin dev-원하는브랜치이름
     * 
     */
}
