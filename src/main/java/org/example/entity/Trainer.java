package org.example.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "name")
@Builder
@Entity
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "trainer", cascade = CascadeType.ALL)
    private List<TrainerCourse> trainerCourses = new ArrayList<>();

    public void addTrainerCourse(TrainerCourse trainerCourse) {
        this.trainerCourses.add(trainerCourse);
        trainerCourse.setTrainer(this);
    }

}
