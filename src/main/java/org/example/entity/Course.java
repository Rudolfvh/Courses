package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cource")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "course_name")
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Students> students = new HashSet<>();

    @Builder.Default
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private Set<TrainerCourse> trainerCourses = new HashSet<>();

    public void addStudent(Students student) {
        this.students.add(student);
        student.setCourse(this);
    }

    public void addTrainerCourse(TrainerCourse trainerCourse) {
        this.trainerCourses.add(trainerCourse);
        trainerCourse.setCourse(this);
    }
}
