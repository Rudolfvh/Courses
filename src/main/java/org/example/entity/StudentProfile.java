package org.example.entity;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "student")
@EqualsAndHashCode(exclude = "student")
@Entity
@Builder
@Table(name = "student_profile")
public class StudentProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double grade;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Students student;

}

