package org.adan.springcloud.msvc.cursos.models.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "cursos_usuarios")
public class CursoAlumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="usuario_id", unique = true)
    private Long alumnoId;


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CursoAlumno)) {
            return false;
        }
        CursoAlumno o = (CursoAlumno) obj;
        return this.alumnoId != null && this.alumnoId.equals(o.alumnoId);
    }
}

