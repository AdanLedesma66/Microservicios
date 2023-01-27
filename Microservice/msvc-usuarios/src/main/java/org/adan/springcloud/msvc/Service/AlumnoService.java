package org.adan.springcloud.msvc.Service;

import org.adan.springcloud.msvc.models.entity.Alumno;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface AlumnoService {
    List<Alumno> alumno_list();
    Optional<Alumno> by_Id(Long id);
    Alumno save_alumno(Alumno alumno);
    void delete_alumno(Long id);
    Optional<Alumno> by_email(String email);
    List<Alumno> listByIds(Iterable<Long> ids);


}
