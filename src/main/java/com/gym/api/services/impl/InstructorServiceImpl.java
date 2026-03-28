package com.gym.api.services.impl;

import com.gym.api.dtos.InstructorRequest;
import com.gym.api.dtos.InstructorResponse;
import com.gym.api.entity.Instructor;
import com.gym.api.repository.GymClassRepository;
import com.gym.api.repository.InstructorRepository;
import com.gym.api.services.InstructorService;

import java.util.List;

public class InstructorServiceImpl implements InstructorService {

    private final InstructorRepository instructorRepo;
    private final GymClassRepository  gymClassRepo;

    public InstructorServiceImpl(InstructorRepository instructorRepo, GymClassRepository gymClassRepo) {
        this.instructorRepo = instructorRepo;
        this.gymClassRepo = gymClassRepo;
    }

    @Override
    public InstructorResponse createInstructor(InstructorRequest request) {
        if (instructorRepo.existByEmal(request.email())){
            throw new RuntimeException("Instructor ya existe");
        }

        Instructor instructor = new Instructor();
                instructor.setFirstName(request.firstname());
                instructor.setLastName(request.lastname());
                instructor.setEmail(request.email());
                instructor.setSpeciality(request.speciality());
                instructor.setActive(true);
                instructor.setPhone(request.phone());

                Instructor savedInstructor = instructorRepo.save(instructor);

                InstructorResponse instrucRes = new InstructorResponse(
                        savedInstructor.getId(),
                        savedInstructor.getFirstName(),
                        savedInstructor.getLastName(),
                        savedInstructor.getEmail(),
                        savedInstructor.getSpeciality(),
                        savedInstructor.getPhone()
                );
        return instrucRes;
    }

    @Override
    public InstructorResponse updateInstructor(Long id, InstructorRequest request) {
        Instructor instructor = instructorRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Instructor no encontrado"));

        if (!instructor.getEmail().equals(request.email()) && instructorRepo.existByEmal(request.email())) {
            throw new RuntimeException("El email ya existe en el sistema");
        }

        instructor.setFirstName(request.firstname());
        instructor.setLastName(request.lastname());
        instructor.setEmail(request.email());
        instructor.setSpeciality(request.speciality());
        instructor.setPhone(request.phone());

        Instructor savedInstructor = instructorRepo.save(instructor);

        InstructorResponse instrucRes = new InstructorResponse(
                savedInstructor.getId(),
                savedInstructor.getFirstName(),
                savedInstructor.getLastName(),
                savedInstructor.getEmail(),
                savedInstructor.getSpeciality(),
                savedInstructor.getPhone()
        );

        return instrucRes;
    }

    @Override
    public void deleteInstructor(Long id) {
        Instructor instructor = instructorRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Instructor no encontrado"));
        instructor.setActive(false);
        instructorRepo.save(instructor);
    }

    @Override
    public InstructorResponse getInstructor(Long id) {
        return instructorRepo.findById(id).map(
                ins -> new InstructorResponse(
                        ins.getId(),
                        ins.getFirstName(),
                        ins.getLastName(),
                        ins.getEmail(),
                        ins.getSpeciality(),
                        ins.getPhone()
                )).orElseThrow(() -> new RuntimeException("Instructor no encontrado"));

    }

    @Override
    public List<InstructorResponse> getAllActiveInstructor() {
        return instructorRepo.findByActiveTrue().stream().map(
                ins -> new InstructorResponse(
                        ins.getId(),
                        ins.getFirstName(),
                        ins.getLastName(),
                        ins.getEmail(),
                        ins.getSpeciality(),
                        ins.getPhone()
                )
        ).toList();
    }
}
