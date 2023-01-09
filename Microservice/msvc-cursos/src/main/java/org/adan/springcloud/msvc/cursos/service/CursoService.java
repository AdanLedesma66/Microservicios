package org.adan.springcloud.msvc.cursos.service;


import org.adan.springcloud.msvc.cursos.models.Alumno;
import org.adan.springcloud.msvc.cursos.models.entity.Curso;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface CursoService{

    //Listar Cursos
    List<Curso> To_List();

    //Buscar por Id el Curso
    Optional<Curso> By_Id(Long id);

    //Buscar por id el curso por el alumno
    Optional<Curso> By_Id_With_Alumno(Long id);

    //Guardar Curso
    Curso Save_Curso (Curso curso);

    //Eliminar curso por id
    void Delete_Curso(Long id);

    //Eliminar CursoAlumono por id
    void Delete_Curso_Alumno_By_Id(Long id);

    //Asignar Alumno a un Curso
    Optional<Alumno> assing_alumno(Alumno alumno, Long cursoId);

    //Crear alumno y asignarlo al curso
    Optional<Alumno> create_alumno(Alumno alumno, Long cursoId);

    //Eliminar alumno de un curso
    Optional<Alumno> Delete_alumno(Alumno alumno, Long cursoId);

    //Listar por id los Alumnos en su curso


}
