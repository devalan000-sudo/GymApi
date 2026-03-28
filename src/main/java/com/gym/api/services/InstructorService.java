package com.gym.api.services;

import com.gym.api.dtos.InstructorRequest;
import com.gym.api.dtos.InstructorResponse;

import java.util.List;

public interface InstructorService {
InstructorResponse createInstructor(InstructorRequest request);
InstructorResponse updateInstructor(Long id, InstructorRequest request);
void deleteInstructor(Long id);
InstructorResponse getInstructor(Long id);
List<InstructorResponse> getAllActiveInstructor();
}
