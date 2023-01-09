package org.adan.springcloud.msvc.controllers;

import org.adan.springcloud.msvc.Service.AlumnoService;
import org.adan.springcloud.msvc.models.entity.Alumno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoService service;

    @GetMapping("/list_alumno")
    public List<Alumno> listar(){
        return service.alumno_list();
    }

    @GetMapping("/list_alumno/{id}")
    public ResponseEntity<?> details(@PathVariable Long id) {
        Optional<Alumno> usuarioOptional = service.by_Id(id);
        if(usuarioOptional.isPresent()){
            return ResponseEntity.ok(usuarioOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create_alumno")
    public ResponseEntity<?> crear(@Valid @RequestBody Alumno alumno, BindingResult result){
        if(!alumno.getEmail().isEmpty() && service.by_email(alumno.getEmail()).isPresent()){
            return ResponseEntity.badRequest().body(Collections
                    .singletonMap("mensaje: ", "Ya existe un usuario con ese correo electronico!"));
        }
        if(result.hasErrors()){
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save_alumno(alumno));
    }

    @PutMapping("/editing_alumno/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Alumno alumno, BindingResult result, @PathVariable Long id) {

        if (result.hasErrors()) {
            return validation(result);
        }

        Optional<Alumno> o = service.by_Id(id);
        if (o.isPresent()) {
            Alumno alumnnoDb = o.get();
            if (!alumno.getEmail().isEmpty() &&
                    !alumno.getEmail().equalsIgnoreCase(alumnnoDb.getEmail()) &&
                    service.by_email(alumno.getEmail()).isPresent()) {
                return ResponseEntity.badRequest()
                        .body(Collections
                                .singletonMap("mensaje", "Ya existe un usuario con ese correo electronico!"));
            }

            alumnnoDb.setNombre(alumno.getNombre());
            alumnnoDb.setEmail(alumno.getEmail());
            alumnnoDb.setPassword(alumno.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save_alumno(alumnnoDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete_alumno/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<Alumno> o = service.by_Id(id);
        if(o.isPresent()){
            service.delete_alumno(id);
            return ResponseEntity.noContent().build();

        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/alumnos-by-curso")
    public ResponseEntity<?> getAlumnosByCurso(@RequestParam List<Long> ids){
        return ResponseEntity.ok(service.listByIds(ids));
    }


    private static ResponseEntity<Map<String, String>> validation(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err ->{
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
