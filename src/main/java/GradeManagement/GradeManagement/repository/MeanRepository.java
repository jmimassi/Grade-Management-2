package GradeManagement.GradeManagement.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import GradeManagement.GradeManagement.model.Mean;

public interface MeanRepository extends JpaRepository<Mean, Long> {
  List<Mean> findBySectionIdAndStudentId(Long sectionId, Long studentId);

  List<Mean> findBySectionId(Long sectionId);

  List<Mean> findByStudentId(Long studentId);

  Mean findByStudentIdAndSectionIdAndSchoolYear(Long studentId, Long sectionId, Integer SchoolYear);

  List<Mean> findByMeanContaining(Integer mean);
  
  @Transactional
  void deleteBySectionIdAndStudentId(long SectionId, Long studentId);
}
