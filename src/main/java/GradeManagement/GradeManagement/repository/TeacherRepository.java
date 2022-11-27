package GradeManagement.GradeManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import GradeManagement.GradeManagement.model.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    List<Teacher> findByNameContaining(String name);

    List<Teacher> findTeachersByCoursesId(Long courseId);
}
