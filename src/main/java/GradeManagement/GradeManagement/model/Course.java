package GradeManagement.GradeManagement.model;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Data;


@Data
@Entity
@Table(name = "courses")
public class Course {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "courses_generator")
    private Long id;

    @Column(name = "name")
    private String name;

    // @ManyToMany(fetch = FetchType.LAZY,
    // cascade = {
    //     CascadeType.PERSIST,
    //     CascadeType.MERGE
    // })
    // @JoinTable(name = "section_course",
    //     joinColumns = { @JoinColumn(name = "course_id") },
    //     inverseJoinColumns = { @JoinColumn(name = "section_id") })
    // private Set<Section> sections = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY,
    cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "teacher_course",
        joinColumns = { @JoinColumn(name = "course_id") },
        inverseJoinColumns = { @JoinColumn(name = "teacher_id") })
    private Set<Teacher> teachers = new HashSet<>();

    public Course() {
    }

    public Course(String name) {
        this.name = name;
      }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // public void addSection(Section section) {
    //     this.sections.add(section);
    //     section.getCourses().add(this);
    //   }
    
    //   public void removeSection(long sectionId) {
    //     Section section = this.sections.stream().filter(t -> t.getId() == sectionId).findFirst().orElse(null);
    //     if (section != null) {
    //       this.sections.remove(section);
    //       section.getCourses().remove(this);
    //     }
    //   }

      public void addTeacher(Teacher teacher) {
        this.teachers.add(teacher);
        teacher.getCourses().add(this);
      }
    
      public void removeTeacher(long teacherId) {
        Teacher teacher = this.teachers.stream().filter(t -> t.getId() == teacherId).findFirst().orElse(null);
        if (teacher != null) {
          this.teachers.remove(teacher);
          teacher.getCourses().remove(this);
        }
      }

      public void setTeachers(Set<Teacher> teachers) {
          this.teachers = teachers;
      }

      public Set<Teacher> getTeachers() {
          return teachers;
      }
    
    @Override
    public String toString() {
        return "Course [id=" + id + ", name=" + name + "]";
    }

}
