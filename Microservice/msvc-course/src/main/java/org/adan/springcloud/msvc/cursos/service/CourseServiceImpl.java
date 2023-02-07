package org.adan.springcloud.msvc.cursos.service;

import org.adan.springcloud.msvc.cursos.clients.AlumnoClientRest;
import org.adan.springcloud.msvc.cursos.models.Student;
import org.adan.springcloud.msvc.cursos.models.entity.Curso;
import org.adan.springcloud.msvc.cursos.models.entity.CursoAlumno;
import org.adan.springcloud.msvc.cursos.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CursoService{

    private final CursoRepository repository;
    private final AlumnoClientRest client;
    @Autowired
    public CourseServiceImpl(CursoRepository repository, AlumnoClientRest client) {
        this.repository = repository;
        this.client = client;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Curso> To_List() {
        return (List<Curso>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> By_Id(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Curso> By_Id_With_Student(Long id) {
        Optional<Curso> byId = repository.findById(id);
        if (byId.isPresent()){
            Curso curso = byId.get();
            if (!curso.getCursoAlumnos().isEmpty()) {
                List<Long> ids = curso.getCursoAlumno().stream().map(cu -> cu.getAlumnoId())
                        .collect(Collectors.toList());

                List<Student> students = client.getAlumnosByCurso(ids);
                curso.setStudents(students);
            }
            return Optional.of(curso);
        }
        return Optional.empty();
    }

    @Override
    public Curso Save_Course(Curso curso) {
        return repository.save(curso);
    }

    @Override
    public void Delete_Course(Long id) {
        repository.deleteById(id);
        client.details(id);
    }

    @Override
    public void Delete_Course_Student_By_Id(Long id) {
        repository.Delete_Course_Student_By_Id(id);
    }

    @Override
    public Optional<Student> assing_student(Student student, Long cursoId) {
        Optional<Curso> o = repository.findById(cursoId);
        if (o.isPresent()) {
            Student studentMsvc = client.details(student.getId());

            Curso curso = o.get();
            CursoAlumno cursoAlumno = new CursoAlumno();
            cursoAlumno.setAlumnoId(studentMsvc.getId());

            curso.addCursoAlumno(cursoAlumno);
            repository.save(curso);
            return Optional.of(studentMsvc);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Student> create_student(Student student, Long cursoId) {
        Optional<Curso> o = repository.findById(cursoId);
        if (o.isPresent()) {
            Student usuarioMsvc = client.create(student);

            Curso curso = o.get();
            CursoAlumno cursoAlumno = new CursoAlumno();
            cursoAlumno.setAlumnoId(usuarioMsvc.getId());

            curso.addCursoAlumno(cursoAlumno);
            repository.save(curso);
            return Optional.of(usuarioMsvc);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Student> Delete_student(Student student, Long cursoId) {
        Optional<Curso> o = repository.findById(cursoId);
        if (o.isPresent()) {
            Student usuarioMsvc = client.details(student.getId());

            Curso curso = o.get();
            CursoAlumno cursoAlumno = new CursoAlumno();
            cursoAlumno.setAlumnoId(usuarioMsvc.getId());

            curso.addCursoAlumno(cursoAlumno);
            repository.save(curso);
            return Optional.of(usuarioMsvc);
        }

        return Optional.empty();
    }




}
