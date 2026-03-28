package com.gym.api.services;

import com.gym.api.dtos.GymClassRequest;
import com.gym.api.dtos.GymClassResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface GymClassService {
    GymClassResponse createClass(GymClassRequest request);
    GymClassResponse updateClass(Long id, GymClassRequest request);
    void deleteClass(Long id);
    GymClassResponse getClass(Long id);
    List<GymClassResponse> getAllActiveClasses();
    List <GymClassResponse> getClassesByDate(LocalDate date);
}
