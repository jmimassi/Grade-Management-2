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
import org.springframework.web.bind.annotation.RestController;

import GradeManagement.GradeManagement.exception.ResourceNotFoundException;
import GradeManagement.GradeManagement.model.Course;
import GradeManagement.GradeManagement.model.Percentage;
import GradeManagement.GradeManagement.model.Section;
import GradeManagement.GradeManagement.repository.CourseRepository;
import GradeManagement.GradeManagement.repository.PercentageRepository;
import GradeManagement.GradeManagement.repository.SectionRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class PercentageController {

  @Autowired
  private CourseRepository courseRepository;

  @Autowired
  private SectionRepository sectionRepository;

  @Autowired
  private PercentageRepository percentageRepository;



  @GetMapping("/percentages") //ok
  public ResponseEntity<List<Percentage>> getAllPercentages() {
      List<Percentage> percentages = new ArrayList<Percentage>();

          percentageRepository.findAll().forEach(percentages::add);

      if (percentages.isEmpty()) {
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(percentages, HttpStatus.OK);
  }

  @GetMapping("/sections/{sectionId}/courses/{courseId}/percentages")
  public ResponseEntity<List<Percentage>> getAllPercentagesByCourseIdAndsectionId(@PathVariable(value = "courseId") Long courseId,@PathVariable(value = "sectionId") Long sectionId) {
    if (!courseRepository.existsById(courseId)) {
      throw new ResourceNotFoundException("Not found course with id = " + courseId);
    }

    if (!sectionRepository.existsById(sectionId)) {
      throw new ResourceNotFoundException("Not found section with id = " + sectionId);
    }

    List<Percentage> percentages = percentageRepository.findBySectionIdAndCourseId(sectionId,courseId);
    return new ResponseEntity<>(percentages, HttpStatus.OK);
  }


  @GetMapping("/sections/{sectionId}/percentages")
  public ResponseEntity<List<Percentage>> getAllPercentagesBySectionId(@PathVariable(value = "sectionId") Long sectionId) {

    if (!sectionRepository.existsById(sectionId)) {
      throw new ResourceNotFoundException("Not found section with id = " + sectionId);
    }

    List<Percentage> percentages = percentageRepository.findBySectionId(sectionId);
    return new ResponseEntity<>(percentages, HttpStatus.OK);
  }

  @GetMapping("/courses/{courseId}/percentages")
  public ResponseEntity<List<Percentage>> getAllPercentagesByCourseId(@PathVariable(value = "CourseId") Long courseId) {

    if (!sectionRepository.existsById(courseId)) {
      throw new ResourceNotFoundException("Not found section with id = " + courseId);
    }

    List<Percentage> percentages = percentageRepository.findByCourseId(courseId);
    return new ResponseEntity<>(percentages, HttpStatus.OK);
  }

  @GetMapping("/percentages/{id}")
  public ResponseEntity<Percentage> getPercentagesById(@PathVariable(value = "id") Long id) {
    Percentage percentage = percentageRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found percentage with id = " + id));

    return new ResponseEntity<>(percentage, HttpStatus.OK);
  }


  @PostMapping("/sections/{sectionId}/courses/{courseId}/percentages")
  public ResponseEntity<Percentage> createPercentage(@PathVariable(value = "sectionId") Long sectionId,@PathVariable(value = "courseId") Long courseId,
      @RequestBody Percentage percentageRequest) {

    // Percentage Percentage = sectionRepository.findById(sectionId).map(section -> {
    //   PercentageRequest.setSection(section);
    // return PercentageRepository.save(PercentageRequest);

    // }).orElseThrow(() -> new ResourceNotFoundException("Not found Course with id = " + sectionId));

      Section section = sectionRepository.findById(sectionId).orElseThrow(() -> new ResourceNotFoundException("Not found Course with id = " + sectionId));
      percentageRequest.setSection(section);
      percentageRepository.save(percentageRequest);

      Course Course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Not found Course with id = " + courseId));
      percentageRequest.setCourse(Course);
      percentageRepository.save(percentageRequest);

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PostMapping("/percentages") //j'ai tenté ca marche pas trop bien
  public ResponseEntity<Percentage> createPercentage(@RequestBody Percentage percentage) {
    Percentage _percentage = percentageRepository.save(new Percentage(percentage.getCourse(),percentage.getSection(),percentage.getPercentage()));
    return new ResponseEntity<>(_percentage, HttpStatus.CREATED);
  }


  @PutMapping("/percentages/{id}")
  public ResponseEntity<Percentage> updatePercentage(@PathVariable("id") long id, @RequestBody Percentage percentageRequest) {
    Percentage percentage = percentageRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("PercentageId " + id + "not found"));

    percentage.setPercentage(percentageRequest.getPercentage());

    return new ResponseEntity<>(percentageRepository.save(percentage), HttpStatus.OK);
  }

  @DeleteMapping("/percentages/{id}")
  public ResponseEntity<HttpStatus> deletePercentage(@PathVariable("id") long id) {
    percentageRepository.deleteById(id);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
  
  @DeleteMapping("/sections/{sectionsId}/courses/{courseId}/percentages")
  public ResponseEntity<List<Percentage>> deleteAllPercentagesOfCourseAndsections(@PathVariable(value = "courseId") Long courseId, @PathVariable(value = "sectionId") Long sectionId) {
    if (!courseRepository.existsById(courseId)) {
      throw new ResourceNotFoundException("Not found course with id = " + courseId);
    }

    if (!sectionRepository.existsById(sectionId)) {
      throw new ResourceNotFoundException("Not found section with id = " + sectionId);
    }

    percentageRepository.deleteBySectionIdAndCourseId(sectionId, courseId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
