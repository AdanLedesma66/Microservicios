package org.adan.springcloud.msvc.repositories;

import org.adan.springcloud.msvc.models.entity.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



import java.util.Optional;
@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {
    @Query("select u from Student u where u.email=?1")
    Optional<Student> findByEmail (String email);



}
