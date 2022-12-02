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
import GradeManagement.GradeManagement.model.Course;
import GradeManagement.GradeManagement.model.GradeTable;
import GradeManagement.GradeManagement.model.Mean;
import GradeManagement.GradeManagement.model.Percentage;
import GradeManagement.GradeManagement.model.Section;
import GradeManagement.GradeManagement.model.Student;
import GradeManagement.GradeManagement.repository.CourseRepository;
import GradeManagement.GradeManagement.repository.GradeTableRepository;
import GradeManagement.GradeManagement.repository.MeanRepository;
import GradeManagement.GradeManagement.repository.PercentageRepository;
import GradeManagement.GradeManagement.repository.SectionRepository;
import GradeManagement.GradeManagement.repository.StudentRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class MeanController {

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private SectionRepository sectionRepository;

  @Autowired
  private MeanRepository meanRepository;

  @Autowired
  private CourseRepository courseRepository;

  @Autowired
  private GradeTableRepository gradeTableRepository;

  @Autowired
  private PercentageRepository percentageRepository;



  @GetMapping("/means") //ok
  public ResponseEntity<List<Mean>> getAllMeans(@RequestParam(required = false) Integer mean, @RequestParam(required = false) Student student, @RequestParam(required = false) Section section) {
      List<Mean> means = new ArrayList<Mean>();

      if (mean == null)
          meanRepository.findAll().forEach(means::add);
      else
          meanRepository.findByMeanContaining(mean).forEach(means::add);

      if (means.isEmpty()) {
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(means, HttpStatus.OK);
  }

  @GetMapping("/sections/{sectionId}/students/{studentId}/means")
  public ResponseEntity<List<Mean>> getAllMeansBystudentIdAndsectionId(@PathVariable(value = "studentId") Long studentId,@PathVariable(value = "sectionId") Long sectionId) {
    if (!studentRepository.existsById(studentId)) {
      throw new ResourceNotFoundException("Not found student with id = " + studentId);
    }

    if (!sectionRepository.existsById(sectionId)) {
      throw new ResourceNotFoundException("Not found section with id = " + sectionId);
    }

    List<Mean> Means = meanRepository.findBySectionIdAndStudentId(sectionId,studentId);
    return new ResponseEntity<>(Means, HttpStatus.OK);
  }


  @GetMapping("/sections/{sectionId}/means")
  public ResponseEntity<List<Mean>> getAllMeansBysectionId(@PathVariable(value = "sectionId") Long sectionId) {

    if (!sectionRepository.existsById(sectionId)) {
      throw new ResourceNotFoundException("Not found section with id = " + sectionId);
    }

    List<Mean> Means = meanRepository.findBySectionId(sectionId);
    return new ResponseEntity<>(Means, HttpStatus.OK);
  }

  @GetMapping("/students/{studentId}/means")
  public ResponseEntity<List<Mean>> getAllMeansBystudentId(@PathVariable(value = "studentId") Long studentId) {

    if (!sectionRepository.existsById(studentId)) {
      throw new ResourceNotFoundException("Not found section with id = " + studentId);
    }

    List<Mean> Means = meanRepository.findByStudentId(studentId);
    return new ResponseEntity<>(Means, HttpStatus.OK);
  }

  @GetMapping("/means/{id}")
  public ResponseEntity<Mean> getMeansById(@PathVariable(value = "id") Long id) {
    Mean mean = meanRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Mean with id = " + id));

    return new ResponseEntity<>(mean, HttpStatus.OK);
  }

  public void InscriptionCours(Long courseId, Long studentId, Integer schoolYear){

    for( int i=1; i<4; i++){
      GradeTable gradeTableRequest = new GradeTable();

      Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Not found student with id = " + courseId));
      gradeTableRequest.setCourse(course);
      gradeTableRepository.save(gradeTableRequest);

      Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Not found student with id = " + studentId));
      gradeTableRequest.setStudent(student);
      gradeTableRepository.save(gradeTableRequest);

      gradeTableRequest.setSchoolYear(schoolYear);
      gradeTableRepository.save(gradeTableRequest);

      gradeTableRequest.setSemester("Q"+String.valueOf(i));
      gradeTableRepository.save(gradeTableRequest);



    };


  };

  @PostMapping("/sections/{sectionId}/students/{studentId}/means")
  public ResponseEntity<Mean> createMean(@PathVariable(value = "sectionId") Long sectionId,@PathVariable(value = "studentId") Long studentId,
      @RequestBody Mean MeanRequest) {

    // Mean mean = sectionRepository.findById(sectionId).map(section -> {
    //   MeanRequest.setSection(section);
    // return MeanRepository.save(MeanRequest);

    // }).orElseThrow(() -> new ResourceNotFoundException("Not found student with id = " + sectionId));;
      Section section = sectionRepository.findById(sectionId).orElseThrow(() -> new ResourceNotFoundException("Not found student with id = " + sectionId));
      MeanRequest.setSection(section);
      Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Not found student with id = " + studentId));
      MeanRequest.setStudent(student);

    meanRepository.save(MeanRequest);





      List<Percentage> percentages = percentageRepository.findBySectionId(sectionId);

      for ( Percentage percent : percentages) {
        Long courseId = percent.getCourse().getId();
        InscriptionCours(courseId,studentId,2022);
      };

    return new ResponseEntity<>(HttpStatus.CREATED);
  }


  @PostMapping("/sections/{sectionId}/students/{studentId}/{schoolYear}/means")
  public ResponseEntity<Mean> createMeanwithSchoolYear(@PathVariable(value = "sectionId") Long sectionId,@PathVariable(value = "studentId") Long studentId,
                                                       @PathVariable(value = "schoolYear") Integer schoolYear, @RequestBody Mean MeanRequest) {

    // Mean mean = sectionRepository.findById(sectionId).map(section -> {
    //   MeanRequest.setSection(section);
    // return MeanRepository.save(MeanRequest);

    // }).orElseThrow(() -> new ResourceNotFoundException("Not found student with id = " + sectionId));
    MeanRequest.setMean(19);
    Section section = sectionRepository.findById(sectionId).orElseThrow(() -> new ResourceNotFoundException("Not found student with id = " + sectionId));
    MeanRequest.setSection(section);
    MeanRequest.setSchoolYear(schoolYear);
    Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Not found student with id = " + studentId));
    MeanRequest.setStudent(student);

    meanRepository.save(MeanRequest);





    List<Percentage> percentages = percentageRepository.findBySectionId(sectionId);

    for ( Percentage percent : percentages) {
      Long courseId = percent.getCourse().getId();
      InscriptionCours(courseId,studentId,schoolYear);
    };

    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PutMapping("/means/{sectionId}/{studentId}")
  public ResponseEntity<Mean> updateMean(@PathVariable("sectionId") long sectionId,@PathVariable("studentId") long studentId, @RequestBody Mean MeanRequest) {
    Mean Mean = meanRepository.findByStudentIdAndSectionIdAndSchoolYear(studentId,sectionId,MeanRequest.getSchoolYear());

    Mean.setMean(MeanRequest.getMean());
    Mean.setSchoolYear(MeanRequest.getSchoolYear());
    return new ResponseEntity<>(meanRepository.save(Mean), HttpStatus.OK);
  }

  @DeleteMapping("/means/{id}")
  public ResponseEntity<HttpStatus> deleteMean(@PathVariable("id") long id) {
    meanRepository.deleteById(id);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
  
  @DeleteMapping("/sections/{sectionsId}/students/{studentId}/means")
  public ResponseEntity<List<Mean>> deleteAllMeansOfstudentAndsections(@PathVariable(value = "studentId") Long studentId, @PathVariable(value = "sectionId") Long sectionId) {
    if (!studentRepository.existsById(studentId)) {
      throw new ResourceNotFoundException("Not found student with id = " + studentId);
    }

    if (!sectionRepository.existsById(sectionId)) {
      throw new ResourceNotFoundException("Not found section with id = " + sectionId);
    }

    meanRepository.deleteBySectionIdAndStudentId(sectionId, studentId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}