package org.adan.springcloud.msvc.cursos.clients;

import org.adan.springcloud.msvc.cursos.models.Alumno;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="msvc-usuarios", url="localhost:8001")
public interface AlumnoClientRest {


    @GetMapping("/{id}")
    Alumno details(@PathVariable Long id);
    @PostMapping("/")
    Alumno create(@RequestBody Alumno usuario);
    @GetMapping("/usuarios-por-curso")
    List<Alumno> obtenerAlumnosPorCurso(@RequestParam Iterable<Long> ids);
}
