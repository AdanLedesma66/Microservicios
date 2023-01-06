package org.adan.springcloud.msvc.Service;

import org.adan.springcloud.msvc.models.entity.Alumno;
import org.adan.springcloud.msvc.repositories.AlumnoRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class AlumnoServiceimpl implements AlumnoService {
    @Autowired
    private AlumnoRepository repository;

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
    }

    @Override
    public Optional<Alumno> by_email(String email) {
        return repository.findByEmail(email);
    }


}
