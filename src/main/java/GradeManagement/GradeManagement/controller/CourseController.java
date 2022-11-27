package GradeManagement.GradeManagement.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import GradeManagement.GradeManagement.model.Course;
import GradeManagement.GradeManagement.model.Teacher;
import GradeManagement.GradeManagement.repository.CourseRepository;
import GradeManagement.GradeManagement.repository.TeacherRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class CourseController {

  @Autowired
  CourseRepository courseRepository;

  @Autowired
  TeacherRepository teacherRepository;

  @GetMapping("/courses")
  public ResponseEntity<List<Course>> getAllCourses(@RequestParam(required = false) String name) {
    List<Course> courses = new ArrayList<Course>();

    if (name == null)
      courseRepository.findAll().forEach(courses::add);
    else
      courseRepository.findByNameContaining(name).forEach(courses::add);

    if (courses.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(courses, HttpStatus.OK);
  }

  @GetMapping("/courses/{id}")
  public ResponseEntity<Course> getCourseById(@PathVariable("id") long id) {
    Course course = courseRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Course with id = " + id));

    return new ResponseEntity<>(course, HttpStatus.OK);
  }

  @PostMapping("/courses")
  public ResponseEntity<Course> createCourse(@RequestBody Course course) {
    Course _course = courseRepository.save(new Course(course.getName()));
    return new ResponseEntity<>(_course, HttpStatus.CREATED);
  }

  @PutMapping("/courses/{id}")
  public ResponseEntity<Course> updateCourse(@PathVariable("id") long id, @RequestBody Course course) {
    Course _course = courseRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Course with id = " + id));

    _course.setName(course.getName());

    return new ResponseEntity<>(courseRepository.save(_course), HttpStatus.OK);
  }

  //employee = course ,  project =  teacher

  @PutMapping("/{courseId}/teacher/{teacherId}") // pour teacher
  public ResponseEntity<Course> assignTeacherToCourse( @PathVariable Long courseId,@PathVariable Long teacherId) {
      Set<Teacher> teacherSet = null;
      Course course = courseRepository.findById(courseId).get();
      Teacher teacher = teacherRepository.findById(teacherId).get();
      teacherSet =  course.getTeachers();
      teacherSet.add(teacher);
      course.setTeachers(teacherSet);
      return new ResponseEntity<>(courseRepository.save(course), HttpStatus.OK);
}

  @DeleteMapping("/courses/{id}")
  public ResponseEntity<HttpStatus> deleteCourse(@PathVariable("id") long id) {
    courseRepository.deleteById(id);
    
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @DeleteMapping("/courses")
  public ResponseEntity<HttpStatus> deleteAllCourses() {
    courseRepository.deleteAll();
    
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
