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
public class TeacherController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    TeacherRepository teacherRepository;

    @GetMapping("/teachers") //ok
    public ResponseEntity<List<Teacher>> getAllTeachers(@RequestParam(required = false) String name) {
        List<Teacher> teachers = new ArrayList<Teacher>();

        if (name == null)
            teacherRepository.findAll().forEach(teachers::add);
        else
            teacherRepository.findByNameContaining(name).forEach(teachers::add);

        if (teachers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    @GetMapping("/teachers/{id}") //ok
    public ResponseEntity<Teacher> getTeacherById(@PathVariable("id") long id) {
        Teacher Teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Teacher with id = " + id));


                //SELECT emp_name FROM springdatajpadb.employee WHERE emp_id IN (SELECT employee_id FROM springdatajpadb.employee_project where project_id IN (SELECT project_id FROM springdatajpadb.project WHERE project_name in ("Java Project")));



        return new ResponseEntity<>(Teacher, HttpStatus.OK);
    }

    @PostMapping("/courses/{courseId}/teachers") //ok
    public ResponseEntity<Teacher> addTeacherByCourse(@PathVariable(value = "courseId") Long courseId, @RequestBody Teacher teacherRequest) {
      Teacher teacher = courseRepository.findById(courseId).map(course -> {
        long teacherId = teacherRequest.getId();
  
        // tag is existed
        if (teacherId != 0L) {
          Teacher _teacher = teacherRepository.findById(teacherId)
                  .orElseThrow(() -> new ResourceNotFoundException("Not found Teacher with id = " + teacherId));
          course.addTeacher(_teacher);
          courseRepository.save(course);
          return _teacher;
        }
  
        // add and create new Tag
        course.addTeacher(teacherRequest);
        return teacherRepository.save(teacherRequest);
      }).orElseThrow(() -> new ResourceNotFoundException("Not found Course with id = " + courseId));
  
      return new ResponseEntity<>(teacher, HttpStatus.CREATED);
    }

    @PostMapping("/teachers")
    public ResponseEntity<Teacher> createTeachers(@RequestBody Teacher teacher) {
      Teacher _teacher = teacherRepository.save(new Teacher(teacher.getName()));
      return new ResponseEntity<>(_teacher,HttpStatus.CREATED);
    }

    @PutMapping("/teachers/{id}") //ok
    public ResponseEntity<Teacher> updateTeacher(@PathVariable("id") long id, @RequestBody Teacher teacher) {
        Teacher _teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Teacher with id = " + id));

        _teacher.setName(teacher.getName());

        return new ResponseEntity<>(teacherRepository.save(_teacher), HttpStatus.OK);
    }



    @PutMapping("/{teacherId}/course/{courseId}")
    public ResponseEntity<Teacher> assignCourseToTeacher( @PathVariable Long teacherId,@PathVariable Long courseId) {
        Set<Course> courseSet = null;
        Teacher teacher = teacherRepository.findById(teacherId).get();
        Course course = courseRepository.findById(courseId).get();
        courseSet =  teacher.getCourses();
        courseSet.add(course);
        teacher.setCourses(courseSet);
        return new ResponseEntity<>(teacherRepository.save(teacher), HttpStatus.OK);
  }

    @DeleteMapping("/teachers/{id}") //ok
    public ResponseEntity<HttpStatus> deleteTeacher(@PathVariable("id") long id) {
        teacherRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // @DeleteMapping("/Teachers") ///ne va plus exister
    // public ResponseEntity<HttpStatus> deleteAllTeachers() {
    //     TeacherRepository.deleteAll();

    //     return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    // }

    @DeleteMapping("/courses/{courseId}/teachers/{teacherId}")
    public ResponseEntity<HttpStatus> deleteTeacherFromCourse(@PathVariable(value = "courseId") Long courseId, @PathVariable(value = "teacherId") Long teacherId) {
      Course course = courseRepository.findById(courseId)
              .orElseThrow(() -> new ResourceNotFoundException("Not found Tutorial with id = " + courseId));
  
      course.removeTeacher(teacherId);
      courseRepository.save(course);
  
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}