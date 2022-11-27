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
public class GradeTableController {

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private CourseRepository courseRepository;

  @Autowired
  private GradeTableRepository gradeTableRepository;

  @Autowired
  private SectionRepository SectionRepository;

  @Autowired
  private PercentageRepository PercentageRepository;

  @Autowired
  private MeanRepository MeanRepository;

  @Autowired
  private PercentageRepository percentageRepository;


  @GetMapping("/courses/{courseId}/students/{studentId}/grade")
  public ResponseEntity<List<GradeTable>> getAllgradeBystudentIdAndCourseId(@PathVariable(value = "studentId") Long studentId,@PathVariable(value = "courseId") Long courseId) {
    if (!studentRepository.existsById(studentId)) {
      throw new ResourceNotFoundException("Not found student with id = " + studentId);
    }

    if (!courseRepository.existsById(courseId)) {
      throw new ResourceNotFoundException("Not found Course with id = " + courseId);
    }

    List<GradeTable> grade = gradeTableRepository.findByCourseIdAndStudentId(courseId,studentId);
    return new ResponseEntity<>(grade, HttpStatus.OK);
  }

  @GetMapping("/grade/{id}")
  public ResponseEntity<GradeTable> getgradeById(@PathVariable(value = "id") Long id) {
    GradeTable gradeTable = gradeTableRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found GradeTable with id = " + id));

    return new ResponseEntity<>(gradeTable, HttpStatus.OK);
  }

  @GetMapping("/grade")
  public ResponseEntity<List<GradeTable>> getAllGrades() {
      List<GradeTable> grades = new ArrayList<GradeTable>();

          gradeTableRepository.findAll().forEach(grades::add);

      if (grades.isEmpty()) {
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      // gradeTableRepository.fetchAllGradeTables().forEach(System.out::println); 
      // System.out.println((gradeTableRepository.fetchAllGradeTables()));
      // List<GradeTable> grady = gradeTableRepository.fetchAllGradeTables();

      return new ResponseEntity<>(grades, HttpStatus.OK);
  }

  @PostMapping("/courses/{courseId}/students/{studentId}/grade")
  public ResponseEntity<GradeTable> createGradeTable(@PathVariable(value = "courseId") Long courseId,@PathVariable(value = "studentId") Long studentId,
      @RequestBody GradeTable gradeTableRequest) {

      Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Not found student with id = " + courseId));
      gradeTableRequest.setCourse(course);
      gradeTableRepository.save(gradeTableRequest);

      Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Not found student with id = " + studentId));
      gradeTableRequest.setStudent(student);
      gradeTableRepository.save(gradeTableRequest);

      // ici que l'on doit add Mean _mean = meanRepository.save(new Mean())

      // List<GradeTable> ListofCourseByStudent = gradeTableRepository.findByCourseIdAndStudentId(courseId,studentId);


      // ici que l'on doit add Mean _mean = meanRepository.save(new Mean())

        
      System.out.println(gradeTableRepository);
      //Inscriptions

    return new ResponseEntity<>(HttpStatus.CREATED);
  }




  @PutMapping("/grade/{id}")
  public ResponseEntity<GradeTable> updateGradeTable(@PathVariable("id") long id, @RequestBody GradeTable gradeTableRequest) {
    GradeTable gradeTable = gradeTableRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("gradeTableId " + id + "not found"));

    // gradeTable.setStudent(gradeTableRequest.getStudent());
    // gradeTable.setCourse(gradeTableRequest.getCourse());
    gradeTable.setSemester(gradeTableRequest.getSemester());
    gradeTable.setSchoolYear(gradeTableRequest.getSchoolYear());
    gradeTable.setComment(gradeTableRequest.getComment());
    gradeTable.setComment_teacher(gradeTableRequest.getComment_teacher());
    gradeTable.setGrade(gradeTableRequest.getGrade());

    return new ResponseEntity<>(gradeTableRepository.save(gradeTable), HttpStatus.OK);

    // Trigger : on modifie (put) une note = note passe de 'null' à une valeur
    //Input : studentId
    //Put(studentID, CourseID, Schoolyear, quadri)
    //trouver la section grace au courseid
    //trouver tous les cours dans la section
    //verifier si tous les grades entre ces cours et l'élève sont pas null
    //si ils ont tous une note, calculer moyenne des dernières notes de chaque course
  }


  @PutMapping("/grade/{courseID}/{studentID}/{SchoolYear}/{semester}")
  public ResponseEntity<GradeTable> updateGrade(@PathVariable("courseID") long courseId,@PathVariable("studentID") long studentId,@PathVariable("SchoolYear") Integer schoolYear,@PathVariable("semester") String semester,@RequestBody GradeTable gradeTableRequest) {
    GradeTable gradeTable = gradeTableRepository.findByCourseIdAndStudentIdAndSchoolYearAndSemester(studentId, courseId, schoolYear, semester);
            // .orElseThrow(() -> new ResourceNotFoundException("gradeTableId " + id + "not found"));

    // gradeTable.setStudent(gradeTableRequest.getStudent());
    // gradeTable.setCourse(gradeTableRequest.getCourse());
    //gradeTable.setSemester(gradeTableRequest.getSemester());
    // gradeTable.setSchoolYear(gradeTableRequest.getSchoolYear());
    gradeTable.setComment(gradeTableRequest.getComment());
    gradeTable.setComment_teacher(gradeTableRequest.getComment_teacher());
    gradeTable.setGrade(gradeTableRequest.getGrade());

    // List<Percentage> percentagesR = PercentageRepository.findByCourseId(courseId);

    List<Mean> means = MeanRepository.findByStudentId(studentId);
    List<Section> sections_with_student = null;   //sections where this student is subscribed
    means.forEach((n) -> sections_with_student.add(n.getSection()));
    List<Percentage> percentages = PercentageRepository.findByCourseId(courseId);
    List<Section> sections_with_course = null;    //sections which contain this course
    percentages.forEach((n) -> sections_with_course.add(n.getSection()));


    for (Section section: sections_with_student){
      if (sections_with_course.contains(section)){  //intersection of the 2 lists
        List<Course> courses = null;
        PercentageRepository.findBySectionId(section.getId()).forEach((n) -> courses.add(n.getCourse()));
        Integer mean = 0;
        for (Course course : courses ){
          GradeTable grade = gradeTableRepository.findByCourseIdAndStudentIdAndSchoolYearAndSemester(course.getId(),studentId, schoolYear, semester);
          if (grade.getGrade() == null) {
            mean = null;
            break;
          }
          Integer percent = percentageRepository.findBySectionIdAndCourseId(section.getId(),course.getId()).get(0).getPercentage();
          mean += grade.getGrade()*percent;


        };
        Mean meanus = MeanRepository.findByStudentIdAndSectionIdAndSchoolYear(section.getId(),studentId,schoolYear);
        meanus.setMean(mean);
      }

    }
    return new ResponseEntity<>(gradeTableRepository.save(gradeTable), HttpStatus.OK);

  }

  @DeleteMapping("/grade/{id}")
  public ResponseEntity<HttpStatus> deleteGradeTable(@PathVariable("id") long id) {
    gradeTableRepository.deleteById(id);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
  
  @DeleteMapping("/courses/{coursesId}/students/{studentId}/grade")
  public ResponseEntity<List<GradeTable>> deleteAllgradeOfstudentAndCourses(@PathVariable(value = "studentId") Long studentId, @PathVariable(value = "courseId") Long courseId) {
    if (!studentRepository.existsById(studentId)) {
      throw new ResourceNotFoundException("Not found student with id = " + studentId);
    }

    if (!courseRepository.existsById(courseId)) {
      throw new ResourceNotFoundException("Not found Course with id = " + courseId);
    }

    gradeTableRepository.deleteByCourseIdAndStudentId(courseId, studentId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
