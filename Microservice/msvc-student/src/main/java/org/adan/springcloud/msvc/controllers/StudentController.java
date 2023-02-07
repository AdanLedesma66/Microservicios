package org.adan.springcloud.msvc.controllers;

import org.adan.springcloud.msvc.Service.StudentService;

import org.adan.springcloud.msvc.models.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService service;

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping("/list_student")
    public List<Student> list(){
        return service.student_list();
    }
    @GetMapping("/list_student/{id}")
    public ResponseEntity<?> details(@PathVariable Long id) {
        Optional<Student> usuarioOptional = service.by_Id(id);
        if(usuarioOptional.isPresent()){
            return ResponseEntity.ok(usuarioOptional.get());
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping("/create_student")
    public ResponseEntity<?> create(@Valid @RequestBody Student student, BindingResult result){
        if(!student.getEmail().isEmpty() && service.by_email(student.getEmail()).isPresent()){
            return ResponseEntity.badRequest().body(Collections
                    .singletonMap("message: ", "There is already a user with that email!"));
        }
        if(result.hasErrors()){
            return validation(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save_student(student));
    }

    @PutMapping("/editing_student/{id}")
    public ResponseEntity<?> edit(@Valid @RequestBody Student student, BindingResult result, @PathVariable Long id) {

        if (result.hasErrors()) {
            return validation(result);
        }

        Optional<Student> o = service.by_Id(id);
        if (o.isPresent()) {
            Student alumnnoDb = o.get();
            if (!student.getEmail().isEmpty() &&
                    !student.getEmail().equalsIgnoreCase(alumnnoDb.getEmail()) &&
                    service.by_email(student.getEmail()).isPresent()) {
                return ResponseEntity.badRequest()
                        .body(Collections
                                .singletonMap("message", "There is already a user with that email!"));
            }

            alumnnoDb.setName(student.getName());
            alumnnoDb.setEmail(student.getEmail());
            alumnnoDb.setPassword(student.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save_student(alumnnoDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete_studemt/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<Student> o = service.by_Id(id);
        if(o.isPresent()){
            service.delete_student(id);

            return ResponseEntity.noContent().build();

        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/student-by-curso/{ids}")
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
