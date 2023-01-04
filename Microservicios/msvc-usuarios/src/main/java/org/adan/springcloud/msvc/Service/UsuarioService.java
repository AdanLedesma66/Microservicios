package org.adan.springcloud.msvc.Service;



import org.adan.springcloud.msvc.models.entity.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface UsuarioService {
    List<Usuario> USUARIO_LIST();
    Optional<Usuario> BY_ID(Long id);
    Usuario SAVE_USER(Usuario usuario);
    void DELETE_USER(Long id);
    List<Usuario> TO_LIST_BY_IDS(Iterable<Long> ids);


    Optional<Usuario> BY_EMAIL(String email);
    boolean EXISTS_BY_EMAIL(String email);
}
