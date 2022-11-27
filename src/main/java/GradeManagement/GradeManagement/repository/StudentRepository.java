package GradeManagement.GradeManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import GradeManagement.GradeManagement.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
    List<Student> findByNameContaining(String name);
}
