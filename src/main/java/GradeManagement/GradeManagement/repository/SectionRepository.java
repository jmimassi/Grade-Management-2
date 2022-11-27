package GradeManagement.GradeManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import GradeManagement.GradeManagement.model.Section;

public interface SectionRepository extends JpaRepository<Section, Long> {
    List<Section> findByNameContaining(String name);
}
