package GradeManagement.GradeManagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import GradeManagement.GradeManagement.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByNameContaining(String name);


}