package com.gym.api.repository;

import com.gym.api.dtos.InstructorRequest;
import com.gym.api.dtos.InstructorResponse;
import com.gym.api.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InstructorRepository extends JpaRepository<Instructor,Long> {
    List<Instructor> findByActiveTrue();
    boolean existByEmal(String emal);
}
