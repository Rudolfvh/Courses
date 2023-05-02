package org.example;

import org.example.entity.*;
import org.junit.jupiter.api.Test;
import lombok.Cleanup;
import org.hibernate.cfg.Configuration;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AppTest {
    @Test
     void saveCourses() {
        Configuration configuration = new Configuration();
        configuration.configure();
        @Cleanup var sessionFactory = configuration.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();
        var courseJavaEE = Course.builder()
                .name("Java Enterprise")
                .build();
        var courseJavaCore = Course.builder()
                .name("Java Core")
                .build();
        var courseJavaTester = Course.builder()
                .name("Java Tester")
                .build();
        session.save(courseJavaEE);
        session.save(courseJavaCore);
        session.save(courseJavaTester);
        session.getTransaction().commit();
    }
    @Test
    void deleteJavaEECourse() {
        Configuration configuration = new Configuration();
        configuration.configure();
        @Cleanup var sessionFactory = configuration.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var query = session.createQuery(" from " + Course.class.getSimpleName());
        var javaEECourse = ((List<Course>) query.list())
                .stream()
                .filter(course -> course.getName().equals("Java Enterprise"))
                .findFirst();
        javaEECourse.ifPresent(session::delete);
        session.getTransaction().commit();
    }
    @Test
    void saveStudent() {
        Configuration configuration = new Configuration();
        configuration.configure();
        @Cleanup var sessionFactory = configuration.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var student = Students.builder()
                .name("Matvey")
                .build();

        var studentProfile = StudentProfile.builder()
                .grade(7.4)
                .build();
        student.setStudentProfile(studentProfile);

        var query = session.createQuery(" from " + Course.class.getSimpleName());

        var javaEECourse = ((List<Course>) query.list())
                .stream()
                .filter(course -> course.getName().equals("Java Enterprise"))
                .findFirst();

        javaEECourse.ifPresent(course -> {
            course.addStudent(student);
            session.save(course);
        });
        session.getTransaction().commit();
    }

    @Test
    void findJavaEEStudents() {
        Configuration configuration = new Configuration();
        configuration.configure();
        @Cleanup var sessionFactory = configuration.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var query = session.createQuery(" from " + Course.class.getSimpleName());

        var javaEECourse = ((List<Course>) query.list())
                .stream()
                .filter(course -> course.getName().equals("Java Enterprise"))
                .findFirst();
        Set<Students> students = new HashSet<>();

        if (javaEECourse.isPresent()) {
            students = javaEECourse.get().getStudents();
        }
        session.getTransaction().commit();
        students.forEach(System.out::println);
    }
    @Test
    void saveStudentProfile() {
        Configuration configuration = new Configuration();
        configuration.configure();
        @Cleanup var sessionFactory = configuration.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var query = session.createQuery(" from " + Students.class.getSimpleName());

        var students = (List<Students>) query.list();

        students.forEach(student -> {
            var studentProfile = StudentProfile.builder()
                    .grade(4 + Math.random() * 4)
                    .build();
            student.setStudentProfile(studentProfile);
            session.save(student);
        });
        session.getTransaction().commit();
    }

    @Test
    void deleteStudents() {
        Configuration configuration = new Configuration();
        configuration.configure();
        @Cleanup var sessionFactory = configuration.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();
        session.beginTransaction();

        var query = session.createQuery(" from " + Course.class.getSimpleName());

        var javaEECourse = ((List<Course>) query.list())
                .stream()
                .filter(course -> course.getName().equals("Java Enterprise"))
                .findFirst();
        javaEECourse.ifPresent(course -> course.getStudents().removeIf(student -> student.getStudentProfile().getGrade() < 6.0));
        session.getTransaction().commit();
    }

    @Test
    void saveTrainer() {
        Configuration configuration = new Configuration();
        configuration.configure();
        @Cleanup var sessionFactory = configuration.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();

        session.beginTransaction();

        var trainer = Trainer.builder()
                .name("Andrey")
                .build();
        session.save(trainer);

        var course = session.get(Course.class, 3L);
        var trainerCourses = TrainerCourse.builder().hours(10).build();

        trainer.addTrainerCourse(trainerCourses);
        course.addTrainerCourse(trainerCourses);
        session.getTransaction().commit();
    }

    @Test
    void updateCourse() {
        Configuration configuration = new Configuration();
        configuration.configure();

        @Cleanup var sessionFactory = configuration.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();

        session.beginTransaction();

        var course = session.get(Course.class, 1L);
        course.setName("Java Enterprise For Pro");

        session.getTransaction().commit();
    }

    @Test
    void deleteCourse() {
        Configuration configuration = new Configuration();
        configuration.configure();

        @Cleanup var sessionFactory = configuration.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();

        session.beginTransaction();

        var course = session.get(Course.class, 3L);
        session.delete(course);

        session.getTransaction().commit();
    }

}
