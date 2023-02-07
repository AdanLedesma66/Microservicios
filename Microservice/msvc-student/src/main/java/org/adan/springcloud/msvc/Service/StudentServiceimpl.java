package org.adan.springcloud.msvc.Service;



import org.adan.springcloud.msvc.models.entity.Student;
import org.adan.springcloud.msvc.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceimpl implements StudentService {

    private final StudentRepository repository;
    @Autowired
    public StudentServiceimpl(StudentRepository repository) {
        this.repository = repository;

    }


    @Override
    public List<Student> student_list() {
        return (List<Student>)repository.findAll();
    }
    @Override
    public Optional<Student> by_Id(Long id) {
        return repository.findById(id);
    }
    @Override
    public Student save_student(Student student) {
        return repository.save(student);
    }
    @Override
    public void delete_student(Long id) {
        repository.deleteById(id);
    }
    @Override
    public Optional<Student> by_email(String email) {
        return repository.findByEmail(email);
    }
    @Override
    public List<Student> listByIds(Iterable<Long> ids) {
        return (List<Student>) repository.findAllById(ids);
    }
}
