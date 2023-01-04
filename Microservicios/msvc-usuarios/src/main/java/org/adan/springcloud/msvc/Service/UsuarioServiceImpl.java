package org.adan.springcloud.msvc.Service;

import org.adan.springcloud.msvc.clients.CursoClienteRest;
import org.adan.springcloud.msvc.models.entity.Usuario;
import org.adan.springcloud.msvc.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    private UsuarioRepository repository;

    private CursoClienteRest client;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> USUARIO_LIST() {
        return (List<Usuario>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> BY_ID(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario SAVE_USER(Usuario usuario) {
        return repository.save(usuario);
    }

    @Override
    @Transactional
    public void DELETE_USER(Long id) {
        repository.deleteById(id);
        client.eliminarCursoUsuarioPorId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> TO_LIST_BY_IDS(Iterable<Long> ids) {
        return (List<Usuario>) repository.findAllById(ids);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> BY_EMAIL(String email) {
        return repository.porEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean EXISTS_BY_EMAIL(String email) {
        return repository.existsByEmail(email);
    }
}
