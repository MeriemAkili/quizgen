package com.quizgen.quizgen.service;

import com.quizgen.quizgen.entity.Question;
import com.quizgen.quizgen.repository.QuestionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class QuizGenerator {

    private final QuestionRepository questionRepository;

    public QuizGenerator(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> generateQuestions(Long courseId, String courseTitle, String difficulty, int numberOfQuestions) {
        List<Question> questions = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < numberOfQuestions; i++) {
            Question q = new Question();
            q.setCourseId(courseId);
            q.setText("Question " + (i + 1) + " : " + generateQuestionText(courseTitle, difficulty));
            q.setOptionA("Reponse A");
            q.setOptionB("Reponse B");
            q.setOptionC("Reponse C");
            q.setOptionD("Reponse D");
            q.setCorrectAnswer(String.valueOf((char) ('A' + random.nextInt(4))));
            q.setExplanation("Explication de la question " + (i + 1));
            q.setStatus("DRAFT");
            questions.add(questionRepository.save(q));
        }

        return questions;
    }

    private String generateQuestionText(String title, String difficulty) {
        String[] templates = {
                "Quel est le concept principal aborde dans le cours ?",
                "Parmi les propositions suivantes, laquelle est correcte ?",
                "Quelle est la definition exacte du sujet ?",
                "Quel est le point cle a retenir ?",
                "Comment se definit le principe fondamental ?"
        };
        int index = Math.abs((title.hashCode() + difficulty.hashCode())) % templates.length;
        return templates[index];
    }
}