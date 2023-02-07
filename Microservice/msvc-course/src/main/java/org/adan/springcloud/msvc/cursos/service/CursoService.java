package org.adan.springcloud.msvc.cursos.service;


import org.adan.springcloud.msvc.cursos.models.Student;
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
    Optional<Curso> By_Id_With_Student(Long id);

    //Guardar Curso
    Curso Save_Course(Curso curso);

    //Eliminar curso por id
    void Delete_Course(Long id);

    //Eliminar CursoAlumono por id
    void Delete_Course_Student_By_Id(Long id);

    //Asignar Alumno a un Curso
    Optional<Student> assing_student(Student student, Long cursoId);

    //Crear alumno y asignarlo al curso
    Optional<Student> create_student(Student student, Long cursoId);

    //Eliminar alumno de un curso
    Optional<Student> Delete_student(Student student, Long cursoId);

    //Listar por id los Alumnos en su curso


}
