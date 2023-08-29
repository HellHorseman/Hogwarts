package ru.hogwarts.school.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.Model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Collection<Student> getAllByAge(int age);

    Collection<Student> findByAgeBetween(int min, int max);

    Collection<Student> findByFaculty_Id(Long facultyId);

    Student findStudentById(Long studentId);

    @Query(value = "SELECT COUNT(*) FROM Student", nativeQuery = true)
    Integer getAllStudentInSchool();

    @Query(value = "SELECT AVG(age) FROM Student", nativeQuery = true)
    Integer getMidlAgeStudent();

    @Query(value = "SELECT * FROM Student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> getFiveLastStudents();
}
