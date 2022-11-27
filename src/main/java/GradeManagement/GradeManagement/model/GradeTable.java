package GradeManagement.GradeManagement.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "gradeTable")
public class GradeTable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gradeTable_generator")
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "student_id", nullable = true)
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "course_id", nullable = true)
    private Course course;

    @Column(name = "semester")
    private String semester;

    @Column(name = "grade")
    private Integer grade;

    @Column(name = "schoolYear")
    private Integer schoolYear;

    @Column(name = "comment")
    private String comment;

    @Column(name = "comment_teacher")
    private String comment_teacher;

    public GradeTable(){
    }

    public GradeTable(Student student, Course course, String semester, Integer grade, String comment, Integer schoolYear, String comment_teacher) {
        this.student = student;
        this.course = course;
        this.semester = semester;
        this.schoolYear = schoolYear;
        this.comment = comment;
        this.comment_teacher = comment_teacher;
        this.grade = grade;
      }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(Integer schoolYear) {
        this.schoolYear = schoolYear;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getComment_teacher() {
        return comment_teacher;
    }

    public void setComment_teacher(String comment_teacher) {
        this.comment_teacher = comment_teacher;
    }
}
