package org.adan.springcloud.msvc.controllers;

import org.adan.springcloud.msvc.Service.UsuarioService;
import org.adan.springcloud.msvc.models.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/users")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping("/tolist")
    public List<Usuario> TOLIST() {
        return service.USUARIO_LIST();
    }

    @GetMapping("/tolist/{id}")
    public ResponseEntity<?> DETAILS(@PathVariable Long id) {
        Optional<Usuario> usuarioOptional = service.BY_ID(id);
        if (usuarioOptional.isPresent()) {
            return ResponseEntity.ok(usuarioOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<?> CREATE(@Valid @RequestBody Usuario usuario, BindingResult result) {

        if (result.hasErrors()) {
            return validar(result);
        }

        if (!usuario.getEmail().isEmpty() && service.EXISTS_BY_EMAIL(usuario.getEmail())) {
            return ResponseEntity.badRequest()
                    .body(Collections
                            .singletonMap("mensaje", "Ya existe un usuario con ese correo electronico!"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.SAVE_USER(usuario));
    }

    @PutMapping("editing/{id}")
    public ResponseEntity<?> EDITING(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id) {

        if (result.hasErrors()) {
            return validar(result);
        }

        Optional<Usuario> o = service.BY_ID(id);
        if (o.isPresent()) {
            Usuario usuarioDb = o.get();
            if (!usuario.getEmail().isEmpty() &&
                    !usuario.getEmail().equalsIgnoreCase(usuarioDb.getEmail()) &&
                    service.BY_EMAIL(usuario.getEmail()).isPresent()) {
                return ResponseEntity.badRequest()
                        .body(Collections
                                .singletonMap("mensaje", "Ya existe un usuario con ese correo electronico!"));
            }

            usuarioDb.setNombre(usuario.getNombre());
            usuarioDb.setEmail(usuario.getEmail());
            usuarioDb.setPassword(usuario.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.SAVE_USER(usuarioDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> DELETE(@PathVariable Long id) {
        Optional<Usuario> o = service.BY_ID(id);
        if (o.isPresent()) {
            service.DELETE_USER(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/user-for-curso")
    public ResponseEntity<?> GETSTUDENTSOERCURSO(@RequestParam List<Long> ids){
        return ResponseEntity.ok(service.TO_LIST_BY_IDS(ids));
    }

    private ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "THIS FIELD" + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
