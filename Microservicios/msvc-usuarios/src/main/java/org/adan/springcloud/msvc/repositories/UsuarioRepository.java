package org.adan.springcloud.msvc.repositories;

import org.adan.springcloud.msvc.models.entity.Usuario;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {
    @Modifying
    @Query("select u from Usuario u where u.email=?1")
    Optional<Usuario> porEmail(String email);
   boolean existsByEmail(String email);

}
