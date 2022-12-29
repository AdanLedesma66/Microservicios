package org.adan.springcloud.msvc.cursos.service;

import org.adan.springcloud.msvc.cursos.models.Usuario;
import org.adan.springcloud.msvc.cursos.models.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {
    List<Curso> CURSO_LIST();
    Optional<Curso> BY_ID(Long id);
    Optional<Curso> BY_ID_USERS (Long id);
    Curso SAVE_CURSO(Curso curso);
    void DELETE_CURSO(Long id);

    void DELETE_CURSO_USER(Long id);

    Optional<Usuario> ASSIGN_USER(Usuario usuario, Long cursoId);
    Optional<Usuario> CREATE_USER(Usuario usuario, Long cursoId);
    Optional<Usuario> DELETE_USER(Usuario usuario, Long cursoId);
}
