package GradeManagement.GradeManagement.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import GradeManagement.GradeManagement.model.GradeTable;

public interface GradeTableRepository extends JpaRepository<GradeTable, Long> {
  List<GradeTable> findByCourseIdAndStudentId(Long courseId, Long studentId);

  GradeTable findByCourseIdAndStudentIdAndSchoolYearAndSemester(Long courseId, Long studentId, Integer SchoolYear, String Semester);
  @Transactional
  void deleteByCourseIdAndStudentId(long courseId, Long studentId);

  // @Query("SELECT g FROM GradeTable s")
  // public List<GradeTable> fetchAllGradeTables();
}


