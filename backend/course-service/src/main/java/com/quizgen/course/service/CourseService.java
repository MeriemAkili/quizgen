package com.quizgen.course.service;

import com.quizgen.course.entity.Course;
import com.quizgen.course.repository.CourseRepository;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final String uploadDir = "uploads";

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();
    }

    public Course uploadCourse(MultipartFile file, String title, Long teacherId, String difficulty, Integer questionCount) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path path = Paths.get(uploadDir, fileName);
        Files.write(path, file.getBytes());

        String text = extractTextFromPdf(path.toFile());

        Course course = new Course();
        course.setTitle(title);
        course.setFileName(file.getOriginalFilename());
        course.setFilePath(path.toString());
        course.setTeacherId(teacherId);
        course.setDifficulty(difficulty);
        course.setQuestionCount(questionCount);
        course.setStatus("PENDING");

        Course saved = courseRepository.save(course);

        System.out.println(">>> PDF recu : " + saved.getTitle() + " (" + text.length() + " caracteres extraits)");
        System.out.println(">>> Status : PENDING (generation IA a venir)");

        return saved;
    }

    public List<Course> getCoursesByTeacher(Long teacherId) {
        return courseRepository.findByTeacherId(teacherId);
    }

    private String extractTextFromPdf(File file) throws IOException {
        try (PDDocument document = Loader.loadPDF(file)) {
            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document);
        }
    }
}