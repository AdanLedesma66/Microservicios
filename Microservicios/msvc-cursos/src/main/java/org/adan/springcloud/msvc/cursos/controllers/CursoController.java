package org.adan.springcloud.msvc.cursos.controllers;

import feign.FeignException;
import org.adan.springcloud.msvc.cursos.models.Usuario;
import org.adan.springcloud.msvc.cursos.models.entity.Curso;
import org.adan.springcloud.msvc.cursos.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService service;

    @GetMapping("/tolist")
    public ResponseEntity<List<Curso>> CURSO_LIST() {
        return ResponseEntity.ok(service.CURSO_LIST());
    }

    @GetMapping("/tolist/{id}")
    public ResponseEntity<?> DETAILS(@PathVariable Long id) {
        Optional<Curso> o = service.BY_ID_WITH_THE_USERS(id);//service.porId(id);
        if (o.isPresent()) {
            return ResponseEntity.ok(o.get());
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping("/create")
    public ResponseEntity<?> CREATE(@Valid @RequestBody Curso curso, BindingResult result) {
        if (result.hasErrors()) {
            return validation(result);
        }
        Curso cursoDb = service.SAVE_CURSO(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoDb);
    }
    @PutMapping("/editing/{id}")
    public ResponseEntity<?> EDITING(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id) {
        if (result.hasErrors()) {
            return validation(result);
        }
        Optional<Curso> o = service.BY_ID(id);
        if (o.isPresent()) {
            Curso cursoDb = o.get();
            cursoDb.setNombre(curso.getNombre());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.SAVE_CURSO(cursoDb));
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> DELETE(@PathVariable Long id) {
        Optional<Curso> o = service.BY_ID(id);
        if (o.isPresent()) {
            service.DELETE_CURSO(o.get().getId());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/assign-user/{cursoId}")
    public ResponseEntity<?> ASSIGN_USERS(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
        Optional<Usuario> o;
        try {
            o = service.ASSIGN_USER(usuario, cursoId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("messaje: ",
                            "The user does not exist by the id or communication error"+ e.getMessage()));
        }
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create-user/{cursoId}")
    public ResponseEntity<?> CREATE_USERS(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
        Optional<Usuario> o;
        try {
            o = service.CREATE_USER(usuario, cursoId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "Failed to create user " +
                            "or communication error" + e.getMessage()));
        }
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete-user/{cursoId}")
    public ResponseEntity<?> DELETE_USERS(@RequestBody Usuario usuario, @PathVariable Long cursoId) {
        Optional<Usuario> o;
        try {
            o = service.DELETE_USER(usuario, cursoId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "The user does not exist " +
                            "by the id or communication error" + e.getMessage()));
        }
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete-curso-user/{id}")
    public ResponseEntity<?> DELETE_CURSO_USERS(@PathVariable Long id){
        service.DELETE_CURSO_USER(id);
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<Map<String, String>> validation(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "this field" + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}