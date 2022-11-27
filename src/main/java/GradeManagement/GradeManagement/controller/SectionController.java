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
import GradeManagement.GradeManagement.model.Section;
import GradeManagement.GradeManagement.repository.CourseRepository;
import GradeManagement.GradeManagement.repository.SectionRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class SectionController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    SectionRepository sectionRepository;

    @GetMapping("/sections") //ok //commestudent
    public ResponseEntity<List<Section>> getAllSections(@RequestParam(required = false) String name) {
        List<Section> sections = new ArrayList<Section>();

        if (name == null)
            sectionRepository.findAll().forEach(sections::add);
        else
            sectionRepository.findByNameContaining(name).forEach(sections::add);

        if (sections.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(sections, HttpStatus.OK);
    }

    @GetMapping("/sections/{id}") //ok
    public ResponseEntity<Section> getSectionById(@PathVariable("id") long id) {
        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Section with id = " + id));

        return new ResponseEntity<>(section, HttpStatus.OK);
    }

    @PostMapping("/sections") ///mm student
    public ResponseEntity<Section> createSection(@RequestBody Section section) {
        Section _section = sectionRepository
                .save(new Section(section.getName(), section.getCredits()));
        return new ResponseEntity<>(_section, HttpStatus.CREATED);
    }


    // @GetMapping("/courses/{courseId}/sections")
    // public ResponseEntity<List<Section>> getAllSectionsByCourseId(@PathVariable(value = "courseId") Long courseId) {
    //   if (!courseRepository.existsById(courseId)) {
    //     throw new ResourceNotFoundException("Not found Course  with id = " + courseId);
    //   }
  
    //   List<Section> sections = sectionRepository.findSectionsByCoursesId(courseId);
    //   return new ResponseEntity<>(sections, HttpStatus.OK);
    // }

    // @PostMapping("/sections")
    // public ResponseEntity<Section> createSection(@RequestBody Section Section) {
    //     Section _section = sectionRepository
    //             .save(new Section(Section.getName()));
    //     return new ResponseEntity<>(_section, HttpStatus.CREATED);
    // }

    // @PostMapping("/courses/{courseId}/sections") //ok
    // public ResponseEntity<Section> addSection(@PathVariable(value = "courseId") Long courseId, @RequestBody Section sectionRequest) {
    //   Section section = courseRepository.findById(courseId).map(course -> {
    //     long sectionId = sectionRequest.getId();
  
    //     // Course is existed
    //     if (sectionId != 0L) {
    //       Section _section = sectionRepository.findById(sectionId)
    //               .orElseThrow(() -> new ResourceNotFoundException("Not found Section with id = " + sectionId));
    //       course.addSection(_section);
    //       courseRepository.save(course);
    //       return _section;
    //     }
  
    //     // add and create new Course
    //     course.addSection(sectionRequest);
    //     return sectionRepository.save(sectionRequest);
    //   }).orElseThrow(() -> new ResourceNotFoundException("Not found Course with id = " + courseId));
  
    //   return new ResponseEntity<>(section, HttpStatus.CREATED);
    // }

    @PutMapping("/sections/{id}") //ok ///mm student
    public ResponseEntity<Section> updateSection(@PathVariable("id") long id, @RequestBody Section section) {
        Section _section = sectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Section with id = " + id));

        _section.setName(section.getName());
        _section.setCredits(section.getCredits());

        return new ResponseEntity<>(sectionRepository.save(_section), HttpStatus.OK);
    }

    @DeleteMapping("/sections/{id}") //ok
    public ResponseEntity<HttpStatus> deleteSection(@PathVariable("id") long id) {
        sectionRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/sections")
    public ResponseEntity<HttpStatus> deleteAllSections() {
        sectionRepository.deleteAll();

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // @DeleteMapping("/courses/{coursesId}/sections/{sectionId}")
    // public ResponseEntity<HttpStatus> deleteSectionFromCourse(@PathVariable(value = "coursesId") Long courseId, @PathVariable(value = "sectionId") Long sectionId) {
    //   Course course = courseRepository.findById(courseId)
    //           .orElseThrow(() -> new ResourceNotFoundException("Not found Section with id = " + courseId));
  
    //   course.removeSection(sectionId);
    //   courseRepository.save(course);
  
    //   return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    // }

}
