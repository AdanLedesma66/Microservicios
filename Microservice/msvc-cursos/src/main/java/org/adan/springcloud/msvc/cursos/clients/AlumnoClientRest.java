package org.adan.springcloud.msvc.cursos.clients;

import org.adan.springcloud.msvc.cursos.models.Alumno;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="msvc-usuarios", url="localhost:8001/alumnos")
public interface AlumnoClientRest {


    @GetMapping("/list_alumno/{id}")
    Alumno details(@PathVariable Long id);
    @PostMapping("/create_alumno")
    Alumno create(@RequestBody Alumno usuario);
    @GetMapping("/alumnos-by-curso")
    List<Alumno> getAlumnosByCurso(@RequestParam Iterable<Long> ids);


}
