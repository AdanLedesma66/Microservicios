package org.adan.springcloud.msvc.cursos.models.entity;


import lombok.Data;
import org.adan.springcloud.msvc.cursos.models.Student;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cursos")
@Data
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String Curso;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "curso_id")
    private List<CursoAlumno> cursoAlumnos;

    @Transient
    @NotNull
    private List<Student> students;

    public Curso() {
        cursoAlumnos = new ArrayList<>();
        students = new ArrayList<>();
    }


    public void addCursoAlumno(CursoAlumno cursoAlumno) {
        cursoAlumnos.add(cursoAlumno);
    }

    public void removeCursoAlumno(CursoAlumno cursoAlumno) {
        cursoAlumnos.remove(cursoAlumno);
    }
    public List<CursoAlumno> getCursoAlumno() {
        return cursoAlumnos;
    }

    public void setCursoAlumno(List<CursoAlumno> cursoAlumnos) {
        this.cursoAlumnos = cursoAlumnos;
    }

    public List<Student> getAlumno() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
