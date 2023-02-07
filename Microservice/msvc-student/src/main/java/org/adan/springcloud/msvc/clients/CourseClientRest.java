package org.adan.springcloud.msvc.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-course", url = "localhost:8002/course")
public interface CourseClientRest {
    @DeleteMapping("/delete-course-student/{id}")
    void deleteCourseStudentById(@PathVariable Long id);
}
