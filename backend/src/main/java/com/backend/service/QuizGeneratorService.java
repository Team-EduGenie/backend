package com.backend.service;

import com.backend.model.*;
import com.backend.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

@Service
public class QuizGeneratorService {
    private static final Logger logger = LoggerFactory.getLogger(QuizGeneratorService.class);

    @Value("${openai.api.key}")
    private String apiKey;

    private final SubjectRepository subjectRepository;
    private final UnitRepository unitRepository;
    private final QuizSetRepository quizSetRepository;
    private final QuizRepository quizRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public QuizGeneratorService(
            SubjectRepository subjectRepository,
            UnitRepository unitRepository,
            QuizSetRepository quizSetRepository,
            QuizRepository quizRepository) {
        this.subjectRepository = subjectRepository;
        this.unitRepository = unitRepository;
        this.quizSetRepository = quizSetRepository;
        this.quizRepository = quizRepository;
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    @Transactional
    public void generateHistoryQuizzes(Unit unit) {
        try {
            // OpenAI API 엔드포인트
            String url = "https://api.openai.com/v1/chat/completions";

            // API 요청 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            // 프롬프트 설정
            String prompt = "당신은 역사 교육 전문가입니다. 다음 조건에 맞는 한국사 문제를 만들어주세요:\n\n" +
                    "1. 문제 형식:\n" +
                    "   - 각 난이도별로 정확히 3개의 객관식 문제 생성 (총 15문제)\n" +
                    "   - 반드시 각 난이도마다 3개씩 문제를 생성해야 함\n" +
                    "   - 모든 문제는 5지선다형\n" +
                    "   - 답은 반드시 1~5 사이의 숫자로만 표시 (보기의 번호)\n" +
                    "   - 모든 문제는 반드시 하나의 명확한 답만 있어야 함\n\n" +
                    "2. 난이도 설명:\n" +
                    "   - 난이도 1 (3문제): 초등 저학년 수준 (예: '신라의 수도는 어디였나요?')\n" +
                    "   - 난이도 2 (3문제): 초등 중학년 수준 (예: '고구려의 가장 유명한 왕은 누구인가요?')\n" +
                    "   - 난이도 3 (3문제): 초등 고학년 수준 (예: '고려의 대표적인 무역항은 어디였나요?')\n" +
                    "   - 난이도 4 (3문제): 중학생 수준 (예: '조선 세종 때 만들어진 한글의 제작 원리는?')\n" +
                    "   - 난이도 5 (3문제): 고등학생 수준 (예: '임진왜란 당시 조선의 외교 전략은?')\n\n" +
                    "3. 주제 다양성:\n" +
                    "   - 시대별로 골고루 분포 (고조선, 삼국, 통일신라, 고려, 조선)\n" +
                    "   - 정치, 경제, 문화, 과학, 예술 등 다양한 분야 포함\n" +
                    "   - 같은 난이도 내에서도 서로 다른 시대나 주제를 다룰 것\n\n" +
                    "4. JSON 형식으로 응답:\n" +
                    "[\n" +
                    "  {\n" +
                    "    \"question\": \"문제\",\n" +
                    "    \"type\": \"objective\",\n" +
                    "    \"options\": [\"보기1\", \"보기2\", \"보기3\", \"보기4\", \"보기5\"],\n" +
                    "    \"answer\": \"1~5 중 정답 번호\",\n" +
                    "    \"difficulty\": 난이도(1-5)\n" +
                    "  }\n" +
                    "]\n\n" +
                    "5. 추가 요구사항:\n" +
                    "   - 보기는 실제 역사적 사실을 바탕으로 구성\n" +
                    "   - 각 난이도에 맞는 어휘와 개념 사용\n" +
                    "   - 보기의 길이는 최대한 비슷하게 구성\n" +
                    "   - 정답은 반드시 1~5 사이의 숫자로만 표시할 것";

            // API 요청 바디 생성
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "gpt-4");
            requestBody.put("messages", Arrays.asList(
                Map.of("role", "system", "content", "당신은 학습자의 수준과 이해도에 맞춰 한국사 문제를 제작하는 전문가입니다. 각 난이도별로 적절한 개념과 용어를 사용하여 학습 효과를 최대화하는 문제를 만들어주세요. 반드시 검증된 역사적 사실에 기반하여 문제를 출제해주세요."),
                Map.of("role", "user", "content", prompt)
            ));
            requestBody.put("temperature", 0.3);

            // API 요청
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(url, request, Map.class);

            // 응답 처리
            if (response.getBody() != null && response.getBody().get("choices") != null) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
                if (!choices.isEmpty()) {
                    String content = (String) ((Map)choices.get(0).get("message")).get("content");
                    logger.info("GPT 응답 원본: {}", content);
                    
                    // 마크다운 포맷팅 제거
                    content = content.replaceAll("```json\\s*", "")
                                   .replaceAll("```\\s*$", "")
                                   .trim();
                    logger.info("포맷팅 제거 후: {}", content);
                    
                    List<Map<String, Object>> allQuizzes = objectMapper.readValue(content, List.class);
                    logger.info("파싱된 퀴즈 개수: {}", allQuizzes.size());

                    // 난이도별로 퀴즈 분류
                    Map<Integer, List<Map<String, Object>>> quizzesByDifficulty = new HashMap<>();
                    for (Map<String, Object> quiz : allQuizzes) {
                        int difficulty = ((Number) quiz.get("difficulty")).intValue();
                        quizzesByDifficulty.computeIfAbsent(difficulty, k -> new ArrayList<>()).add(quiz);
                    }

                    // 각 난이도별로 퀴즈 세트 생성
                    for (int difficulty = 1; difficulty <= 5; difficulty++) {
                        List<Map<String, Object>> difficultyQuizzes = quizzesByDifficulty.get(difficulty);
                        if (difficultyQuizzes != null && !difficultyQuizzes.isEmpty()) {
                            // 퀴즈 세트 생성
                            QuizSet quizSet = new QuizSet();
                            quizSet.setUnit(unit);
                            quizSet.setQuizName(String.format("사회_역사_난이도%d", difficulty));
                            quizSet.setQuizDiff(difficulty);
                            quizSetRepository.save(quizSet);

                            // 해당 난이도의 퀴즈 3개만 저장
                            for (int i = 0; i < Math.min(3, difficultyQuizzes.size()); i++) {
                                Map<String, Object> quizData = difficultyQuizzes.get(i);
                                Quiz quiz = new Quiz();
                                String question = (String) quizData.get("question");
                                
                                // 객관식 문제인 경우 보기도 포함
                                if ("objective".equals(quizData.get("type"))) {
                                    List<String> options = (List<String>) quizData.get("options");
                                    question += "\n\n보기:\n";
                                    for (int j = 0; j < options.size(); j++) {
                                        question += (j + 1) + ". " + options.get(j) + "\n";
                                    }
                                }
                                
                                quiz.setProblem(question);
                                quiz.setRightAnswer(quizData.get("answer").toString());
                                quiz.setQuizSet(quizSet);
                                quizRepository.save(quiz);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("역사 퀴즈 생성 중 오류 발생: ", e);
            throw new RuntimeException("역사 퀴즈 생성에 실패했습니다.", e);
        }
    }

    @Transactional
    public void initializeSubjectsAndUnits() {
        // 사회 과목 생성 또는 조회
        Subject socialStudies = subjectRepository.findBySubjectName("사회")
                .orElseGet(() -> {
                    Subject newSubject = new Subject();
                    newSubject.setSubjectName("사회");
                    return subjectRepository.save(newSubject);
                });

        // 역사 단원 생성 또는 조회
        Unit historyUnit = unitRepository.findByUnitNameAndSubject("역사", socialStudies)
                .orElseGet(() -> {
                    Unit newUnit = new Unit();
                    newUnit.setUnitName("역사");
                    newUnit.setSubject(socialStudies);
                    return unitRepository.save(newUnit);
                });

        // 역사 문제 생성
        generateHistoryQuizzes(historyUnit);
    }
} 