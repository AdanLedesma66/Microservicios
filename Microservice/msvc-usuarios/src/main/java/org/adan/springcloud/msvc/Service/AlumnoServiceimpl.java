package org.adan.springcloud.msvc.Service;

import org.adan.springcloud.msvc.clients.CursoClientRest;
import org.adan.springcloud.msvc.models.entity.Alumno;
import org.adan.springcloud.msvc.repositories.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlumnoServiceimpl implements AlumnoService {
    @Autowired
    private AlumnoRepository repository;
    @Autowired
    private CursoClientRest client;

    @Override
    public List<Alumno> alumno_list() {
        return (List<Alumno>)repository.findAll();
    }

    @Override
    public Optional<Alumno> by_Id(Long id) {
        return repository.findById(id);
    }

    @Override
    public Alumno save_alumno(Alumno alumno) {
        return repository.save(alumno);
    }

    @Override
    public void delete_alumno(Long id) {
        repository.deleteById(id);
        client.deleteCursoAlumnoById(id);
    }

    @Override
    public Optional<Alumno> by_email(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public List<Alumno> listByIds(Iterable<Long> ids) {
        return (List<Alumno>) repository.findAllById(ids);
    }


}
