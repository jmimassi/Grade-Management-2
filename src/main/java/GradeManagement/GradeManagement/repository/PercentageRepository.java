package GradeManagement.GradeManagement.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import GradeManagement.GradeManagement.model.Percentage;

public interface PercentageRepository extends JpaRepository<Percentage, Long> {
  List<Percentage> findBySectionIdAndCourseId(Long sectionId, Long courseId);

  List<Percentage> findBySectionId(Long sectionId);

  List<Percentage> findByCourseId(Long courseId);

  List<Percentage> findByPercentageContaining(Integer Percentage);
  
  @Transactional
  void deleteBySectionIdAndCourseId(long SectionId, Long courseId);
}
