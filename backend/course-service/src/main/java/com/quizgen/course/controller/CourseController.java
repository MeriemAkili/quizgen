package com.quizgen.course.controller;

import com.quizgen.course.entity.Course;
import com.quizgen.course.service.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Course> uploadCourse(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("teacherId") Long teacherId,
            @RequestParam("difficulty") String difficulty,
            @RequestParam("questionCount") Integer questionCount) throws IOException {
        Course course = courseService.uploadCourse(file, title, teacherId, difficulty, questionCount);
        return ResponseEntity.ok(course);
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<Course>> getCoursesByTeacher(@PathVariable Long teacherId) {
        return ResponseEntity.ok(courseService.getCoursesByTeacher(teacherId));
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Course service OK");
    }
}