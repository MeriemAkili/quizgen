package com.quizgen.quizgen.controller;

import com.quizgen.quizgen.entity.Question;
import com.quizgen.quizgen.service.QuizGenerator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz-generation")
public class QuizGenerationController {

    private final QuizGenerator quizGenerator;

    public QuizGenerationController(QuizGenerator quizGenerator) {
        this.quizGenerator = quizGenerator;
    }

    @PostMapping("/generate")
    public ResponseEntity<List<Question>> generate(
            @RequestParam Long courseId,
            @RequestParam String courseTitle,
            @RequestParam String difficulty,
            @RequestParam Integer numberOfQuestions) {
        List<Question> questions = quizGenerator.generateQuestions(courseId, courseTitle, difficulty, numberOfQuestions);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Quiz generation service OK");
    }
}