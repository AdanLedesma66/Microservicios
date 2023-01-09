package org.adan.springcloud.msvc.cursos.repository;

import org.adan.springcloud.msvc.cursos.models.Alumno;
import org.adan.springcloud.msvc.cursos.models.entity.Curso;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CursoRepository extends CrudRepository<Curso, Long> {

    @Modifying
    @Query("delete from CursoAlumno cu where cu.alumnoId=?1")

    void Delete_Curso_Alumno_By_Id(Long id);


}
