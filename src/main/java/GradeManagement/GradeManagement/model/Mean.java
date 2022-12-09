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
@Table(name = "mean")
public class Mean {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mean_generator")
    private Long id;

    @Column(name = "mean")
    private Integer mean;

    @Column(name = "schoolYear")
    private Integer schoolYear;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "student_id", nullable = true)
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "section_id", nullable = true)
    private Section section;


    public Mean(){
    }

    public Mean(Student student, Section section, Integer mean, Integer schoolYear) {
        this.student = student;
        this.section = section;
        this.mean = mean;
        this.schoolYear = schoolYear;
      }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Integer getMean() {
        return mean;
    }

    public void setMean(Integer mean) {
        this.mean = mean;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Integer getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(Integer schoolYear) {
        this.schoolYear = schoolYear;
    }


}
