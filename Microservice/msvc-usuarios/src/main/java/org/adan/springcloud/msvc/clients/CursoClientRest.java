package org.adan.springcloud.msvc.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
@FeignClient(name="msvc-cursos", url = "host.docker.internal:8002/cursos")
public interface CursoClientRest {

    @DeleteMapping("/eliminar-curso-alumno/{id}")
    void deleteCursoAlumnoById(@PathVariable Long id);
}
