package org.adan.springcloud.msvc.cursos.models;


import lombok.Data;

@Data
public class Alumno {

    private Long id;

    private String nombre;

    private String email;

    private String password;

}
