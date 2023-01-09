package org.adan.springcloud.msvc.cursos.controllers;

import feign.FeignException;
import org.adan.springcloud.msvc.cursos.models.Alumno;
import org.adan.springcloud.msvc.cursos.models.entity.Curso;
import org.adan.springcloud.msvc.cursos.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
@RestController
@RequestMapping("/cursos")
public class CursoController {

    @Autowired
    private CursoService service;

    @GetMapping("/list_curso")
    public ResponseEntity<List<Curso>> List(){
        return ResponseEntity.ok(service.To_List());
    }

    @GetMapping("/list_curso/{id}")
    public ResponseEntity<?> Curso_Details(@PathVariable Long id){
        Optional<Curso> o = service.By_Id_With_Alumno(id);
            if (o.isPresent()){
                return ResponseEntity.ok(o.get());
            }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/curso_create")
    public ResponseEntity<?> Create(@Valid @RequestBody Curso curso, BindingResult result){
            if (result.hasErrors()){
                return validation(result);
            }
            Curso cursoDb = service.Save_Curso(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoDb);
    }

    @PutMapping("/curso_editing/{id}")
    public ResponseEntity<?> Editing(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id){
        if (result.hasErrors()){
            return validation(result);
        }
        Optional<Curso> o = service.By_Id(id);
        if (o.isPresent()){
            Curso cursoDb =  o.get();
            cursoDb.setCurso(curso.getCurso());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.Save_Curso(cursoDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/curso_delete/{id}")
    public ResponseEntity<?> Delete(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id){
        Optional<Curso> o = service.By_Id(id);
        if (o.isPresent()){
            service.Delete_Curso(o.get().getId());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    //Procedimientos dentro del Msvc_Alumnos

    @PutMapping("/assing_alumno/{cursoId}")
    public ResponseEntity<?> assing(@RequestBody Alumno alumno, @PathVariable Long cursoId){
        Optional<Alumno> o;
        try {
            o = service.assing_alumno(alumno, cursoId);
        }catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Mensaje: ", "No existe el codigo por id o error en la comunicacion" + e.getMessage()));
        }
        if (o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create_curso_alumno/{cursoId}")
    public ResponseEntity<?> CrearAlumno(@RequestBody Alumno usuario, @PathVariable Long cursoId) {
        Optional<Alumno> o;
        try {
            o = service.create_alumno(usuario, cursoId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "No existe el usuario por el id o error en la comunicacion: " + e.getMessage()));
        }
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete_alumno/{cursoId}")
    public ResponseEntity<?> delete_alumno (@RequestBody Alumno alumno, @PathVariable Long cursoId){
        Optional<Alumno> o;
        try {
            o = service.Delete_alumno(alumno, cursoId);
        }catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Mensaje: ", "No se pudo crear el alumno o error en la comunicacion" + e.getMessage()));
        }
        if (o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete-curso-alumno/{id}")
    public ResponseEntity<?> eliminarCursoUsuarioPorId(@PathVariable Long id){
        service.Delete_Curso_Alumno_By_Id(id);
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<Map<String, String>> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
     }
}
