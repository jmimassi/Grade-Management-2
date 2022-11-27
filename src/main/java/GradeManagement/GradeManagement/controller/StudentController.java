package GradeManagement.GradeManagement.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import GradeManagement.GradeManagement.exception.ResourceNotFoundException;
import GradeManagement.GradeManagement.model.Student;
import GradeManagement.GradeManagement.repository.StudentRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class StudentController {

    @Autowired
    StudentRepository studentRepository;

    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents(@RequestParam(required = false) String name) {
        List<Student> students = new ArrayList<Student>();

        if (name == null)
            studentRepository.findAll().forEach(students::add);
        else
            studentRepository.findByNameContaining(name).forEach(students::add);

        if (students.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable("id") long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Student with id = " + id));

        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PostMapping("/students")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        Student _student = studentRepository
                .save(new Student(student.getName()));
        return new ResponseEntity<>(_student, HttpStatus.CREATED);
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable("id") long id, @RequestBody Student student) {
        Student _student = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found student with id = " + id));

        _student.setName(student.getName());

        return new ResponseEntity<>(studentRepository.save(_student), HttpStatus.OK);
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<HttpStatus> deleteStudent(@PathVariable("id") long id) {
        studentRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/students")
    public ResponseEntity<HttpStatus> deleteAllStudents() {
        studentRepository.deleteAll();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
