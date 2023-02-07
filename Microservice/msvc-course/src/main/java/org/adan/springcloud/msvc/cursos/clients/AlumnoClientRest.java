package org.adan.springcloud.msvc.cursos.clients;

import org.adan.springcloud.msvc.cursos.models.Student;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="msvc-student", url="msvc-student:8001/student")
public interface AlumnoClientRest {

    @GetMapping("/list_student/{id}")
    Student details(@PathVariable Long id);
    @PostMapping("/create_student")
    Student create(@RequestBody Student usuario);
    @GetMapping("/student-by-curso")
    List<Student> getAlumnosByCurso(@RequestParam Iterable<Long> ids);


}
