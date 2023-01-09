package org.adan.springcloud.msvc.cursos.service;

import org.adan.springcloud.msvc.cursos.clients.AlumnoClientRest;
import org.adan.springcloud.msvc.cursos.models.Alumno;
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
public class CursoServiceImpl implements CursoService{

    @Autowired
    private CursoRepository repository;
    @Autowired
    private AlumnoClientRest client;

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
    public Optional<Curso> By_Id_With_Alumno(Long id) {
        Optional<Curso> byId = repository.findById(id);
        if (byId.isPresent()){
            Curso curso = byId.get();
            if (!curso.getCursoAlumnos().isEmpty()) {
                List<Long> ids = curso.getCursoAlumno().stream().map(cu -> cu.getAlumnoId())
                        .collect(Collectors.toList());

                List<Alumno> alumnos = client.getAlumnosByCurso(ids);
                curso.setAlumnos(alumnos);
            }
            return Optional.of(curso);
        }
        return Optional.empty();
    }

    @Override
    public Curso Save_Curso(Curso curso) {
        return repository.save(curso);
    }

    @Override
    public void Delete_Curso(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void Delete_Curso_Alumno_By_Id(Long id) {
        repository.Delete_Curso_Alumno_By_Id(id);
    }

    @Override
    public Optional<Alumno> assing_alumno(Alumno alumno, Long cursoId) {
        Optional<Curso> o = repository.findById(cursoId);
        if (o.isPresent()) {
            Alumno alumnoMsvc = client.details(alumno.getId());

            Curso curso = o.get();
            CursoAlumno cursoAlumno = new CursoAlumno();
            cursoAlumno.setAlumnoId(alumnoMsvc.getId());

            curso.addCursoAlumno(cursoAlumno);
            repository.save(curso);
            return Optional.of(alumnoMsvc);
        }

        return Optional.empty();
    }

    @Override
    public Optional<Alumno> create_alumno(Alumno alumno, Long cursoId) {
        Optional<Curso> o = repository.findById(cursoId);
        if (o.isPresent()) {
            Alumno usuarioMsvc = client.create(alumno);

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
    public Optional<Alumno> Delete_alumno(Alumno alumno, Long cursoId) {
        Optional<Curso> o = repository.findById(cursoId);
        if (o.isPresent()) {
            Alumno usuarioMsvc = client.details(alumno.getId());

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
