package org.adan.springcloud.msvc.Service;

import org.adan.springcloud.msvc.models.entity.Student;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface StudentService {
    List<Student> student_list();
    Optional<Student> by_Id(Long id);
    Student save_student(Student student);
    void delete_student(Long id);
    Optional<Student> by_email(String email);
    List<Student> listByIds(Iterable<Long> ids);


}
