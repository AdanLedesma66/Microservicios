package org.adan.springcloud.msvc.cursos.clients;

import org.adan.springcloud.msvc.cursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="msvc-usuarios", url="localhost:8001/users")
public interface UsuarioClientRest {

    @GetMapping("/tolist/{id}")
    Usuario DETAILS(@PathVariable Long id);

    @PostMapping("/create")
    Usuario CREATE(@RequestBody Usuario usuario);

    @GetMapping("/user-for-curso")
    List<Usuario> GETSTUDENTSPERCOURSE(@RequestParam Iterable<Long> ids);
}
