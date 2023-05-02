package org.example.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "students")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "course")
@EqualsAndHashCode(of = "name")
public class Students {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    private StudentProfile studentProfile;

    public void setStudentProfile(StudentProfile studentProfile) {
        this.studentProfile = studentProfile;
        this.studentProfile.setStudent(this);
    }
}
