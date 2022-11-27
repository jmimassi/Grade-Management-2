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
@Table(name = "percentage")
public class Percentage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "percentage_generator")
    private Long id;

    @Column(name = "percentage")
    private Integer percentage;
    
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "course_id", nullable = true)
    private Course course;

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "section_id", nullable = true)
    private Section section;


    public Percentage(){
    }

    public Percentage(Course course, Section section, Integer percentage) {
        this.course = course;
        this.section = section;
        this.percentage = percentage;
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

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }


}
