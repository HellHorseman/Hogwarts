SELECT s.name AS StudentName, s.age, f.name AS FacultyName FROM Student s JOIN Faculty f ON s.faculty_id = f.id;

SELECT s.name AS StudentName, s.age FROM Student s JOIN Avatar a ON s.id = a.student_id;