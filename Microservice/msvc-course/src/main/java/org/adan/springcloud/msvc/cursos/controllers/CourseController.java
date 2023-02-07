package org.adan.springcloud.msvc.cursos.controllers;

import feign.FeignException;
import org.adan.springcloud.msvc.cursos.models.Student;
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
@RequestMapping("/course")
public class CourseController {
    private final CursoService service;
    @Autowired
    public CourseController(CursoService service) {
        this.service = service;
    }

    @GetMapping("/list_course")
    public ResponseEntity<List<Curso>> List(){
        return ResponseEntity.ok(service.To_List());
    }

    @GetMapping("/list_course/{id}")
    public ResponseEntity<?> Curso_Details(@PathVariable Long id){
        Optional<Curso> o = service.By_Id_With_Student(id);
            if (o.isPresent()){
                return ResponseEntity.ok(o.get());
            }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/course_create")
    public ResponseEntity<?> Create(@Valid @RequestBody Curso curso, BindingResult result){
            if (result.hasErrors()){
                return validation(result);
            }
            Curso cursoDb = service.Save_Course(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoDb);
    }

    @PutMapping("/course_editing/{id}")
    public ResponseEntity<?> Editing(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id){
        if (result.hasErrors()){
            return validation(result);
        }
        Optional<Curso> o = service.By_Id(id);
        if (o.isPresent()){
            Curso cursoDb =  o.get();
            cursoDb.setCurso(curso.getCurso());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.Save_Course(cursoDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/course_delete/{id}")
    public ResponseEntity<?> Delete(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id){
        Optional<Curso> o = service.By_Id(id);
        if (o.isPresent()){
            service.Delete_Course(o.get().getId());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    //Procedimientos dentro del Msvc_Alumnos

    @PutMapping("/assing_student/{courseId}")
    public ResponseEntity<?> assing(@RequestBody Student student, @PathVariable Long cursoId){
        Optional<Student> o;
        try {
            o = service.assing_student(student, cursoId);
        }catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Message: ", "No existe el codigo por id o error en la comunicacion" + e.getMessage()));
        }
        if (o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create_course_student/{courseId}")
    public ResponseEntity<?> CrearAlumno(@RequestBody Student usuario, @PathVariable Long cursoId) {
        Optional<Student> o;
        try {
            o = service.create_student(usuario, cursoId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", "User does not exist due to id or communication error: " + e.getMessage()));
        }
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete_student/{courseId}")
    public ResponseEntity<?> delete_alumno (@RequestBody Student student, @PathVariable Long cursoId){
        Optional<Student> o;
        try {
            o = service.Delete_student(student, cursoId);
        }catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Mensaje: ", "Failed to create student or communication error" + e.getMessage()));
        }
        if (o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete-course-student/{id}")
    public ResponseEntity<?> deleteCourseStudentById(@PathVariable Long id){
        service.Delete_Course_Student_By_Id(id);

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
