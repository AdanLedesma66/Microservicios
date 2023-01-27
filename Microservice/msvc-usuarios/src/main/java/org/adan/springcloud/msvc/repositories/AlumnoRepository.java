package org.adan.springcloud.msvc.repositories;

import org.adan.springcloud.msvc.models.entity.Alumno;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



import java.util.Optional;
@Repository
public interface AlumnoRepository extends CrudRepository<Alumno, Long> {
    @Query("select u from Alumno u where u.email=?1")
    Optional<Alumno> findByEmail (String email);



}
