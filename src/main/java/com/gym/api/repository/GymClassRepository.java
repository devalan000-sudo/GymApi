package com.gym.api.repository;

import com.gym.api.entity.GymClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface GymClassRepository extends JpaRepository<GymClass, Long> {
    List<GymClass> findByActiveTrue();
    List<GymClass> findByStartTimeBetweenAndActiveTrue(LocalDateTime start, LocalDateTime end);
    List<GymClass> findByInstructorIdAndActiveTrue(Long id);
    //Consulta personalizada
    @Query("SELECT g FROM GymClass g WHERE g.room = :room AND g.active = true")
    List<GymClass> findActiveClassesInRoom(@Param("room") int room);
}
