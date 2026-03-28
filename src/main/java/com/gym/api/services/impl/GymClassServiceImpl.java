package com.gym.api.services.impl;

import com.gym.api.dtos.GymClassRequest;
import com.gym.api.dtos.GymClassResponse;
import com.gym.api.services.GymClassService;

import java.time.LocalDate;
import java.util.List;

public class GymClassServiceImpl implements GymClassService {
    @Override
    public GymClassResponse createClass(GymClassRequest request) {
        return null;
    }

    @Override
    public GymClassResponse updateClass(Long id, GymClassRequest request) {
        return null;
    }

    @Override
    public void deleteClass(Long id) {

    }

    @Override
    public GymClassResponse getClass(Long id) {
        return null;
    }

    @Override
    public List<GymClassResponse> getAllActiveClasses() {
        return List.of();
    }

    @Override
    public List<GymClassResponse> getClassesByDate(LocalDate date) {
        return List.of();
    }
}
