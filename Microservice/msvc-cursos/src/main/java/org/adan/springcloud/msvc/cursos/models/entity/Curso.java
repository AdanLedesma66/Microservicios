package org.adan.springcloud.msvc.cursos.models.entity;


import lombok.Data;
import org.adan.springcloud.msvc.cursos.models.Alumno;


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
    private List<Alumno> alumnos;

    public Curso() {
        cursoAlumnos = new ArrayList<>();
        alumnos = new ArrayList<>();
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

    public List<Alumno> getAlumno() {
        return alumnos;
    }

    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }
}
