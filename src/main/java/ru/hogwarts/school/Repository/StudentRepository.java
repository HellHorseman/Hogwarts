package ru.hogwarts.school.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.Model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
