package org.adan.springcloud.msvc.repositories;

import org.adan.springcloud.msvc.models.entity.Alumno;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AlumnoRepository extends CrudRepository<Alumno, Long> {
    Optional<Alumno> findByEmail (String email);
}
