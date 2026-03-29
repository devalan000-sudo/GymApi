package com.gym.api.services.impl;

import com.gym.api.dtos.GymClassRequest;
import com.gym.api.dtos.GymClassResponse;
import com.gym.api.entity.GymClass;
import com.gym.api.entity.Instructor;
import com.gym.api.repository.GymClassRepository;
import com.gym.api.repository.InstructorRepository;
import com.gym.api.services.GymClassService;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class GymClassServiceImpl implements GymClassService {


    private final GymClassRepository gymClassRepo;
    private final InstructorRepository instructorRepo;

    public GymClassServiceImpl(GymClassRepository gymClassRepo, InstructorRepository instructorRepo) {
        this.gymClassRepo = gymClassRepo;
        this.instructorRepo = instructorRepo;
    }

    @Override
    public GymClassResponse createClass(GymClassRequest request) {
        Instructor instructor = instructorRepo.findById(request.instructorId()).orElseThrow(
                () -> new RuntimeException("instructor not found")
        );

        LocalDateTime newClassStart = request.startTime();
        LocalDateTime newClassEnd = newClassStart.plusMinutes(request.durationMinutes());

        List<GymClass> classesInRoom = gymClassRepo.findActiveClassesInRoom(request.room());
        for (GymClass c : classesInRoom) {
            LocalDateTime cEnd = c.getStartTime().plusMinutes(c.getDurationMinutes());
            if (newClassStart.isBefore(cEnd) && newClassEnd.isAfter(c.getStartTime())){
                throw new RuntimeException("El salon " + request.room() + " ya esta ocupado");
            }
        }

        List<GymClass> instructorClasses = gymClassRepo.findByInstructorIdAndActiveTrue(instructor.getId());
        for (GymClass c : instructorClasses) {
            LocalDateTime cEnd = c.getStartTime().plusMinutes(c.getDurationMinutes());
            if (newClassStart.isBefore(cEnd) && newClassEnd.isAfter(c.getStartTime())){
                throw new RuntimeException("El instructor " + instructor.getFirstName() + " ya tiene otra clase en este horario");
            }
        }

        GymClass gymClass = new GymClass();
        gymClass.setActType(request.actType());
        gymClass.setDescription(request.description());
        gymClass.setCapacity(request.capacity());
        gymClass.setRoom(request.room());
        gymClass.setStartTime(newClassStart);
        gymClass.setDurationMinutes(request.durationMinutes());
        gymClass.setInstructor(instructor);
        gymClass.setActive(true);

        GymClass saved = gymClassRepo.save(gymClass);

        GymClassResponse gymClassResponse = new GymClassResponse(
                saved.getId(),
                saved.getActType(),
                saved.getDescription(),
                saved.getCapacity(),
                saved.getRoom(),
                saved.getStartTime(),
                saved.getStartTime().plusMinutes(saved.getDurationMinutes()),
                saved.getInstructor().getFirstName() + " " + saved.getInstructor().getLastName()
        );
        return gymClassResponse;
    }

    @Override
    public GymClassResponse updateClass(Long id, GymClassRequest request) {
        GymClass gymClass = gymClassRepo.findById(id).orElseThrow(() ->
                new RuntimeException("Clase no encontrada"));

        Instructor instructor = instructorRepo.findById(request.instructorId()).orElseThrow(() ->
                new RuntimeException("Instructor no encontrado"));

        LocalDateTime newStart = request.startTime();
        LocalDateTime newEnd = newStart.plusMinutes(request.durationMinutes());

        List<GymClass> classInRoom = gymClassRepo.findActiveClassesInRoom(request.room());
        for (GymClass exisiting : classInRoom){
            if (exisiting.getId().equals(id)) continue;
            LocalDateTime existingEnd =  exisiting.getStartTime().plusMinutes(exisiting.getDurationMinutes());
            if (newStart.isBefore(existingEnd) && newEnd.isAfter(existingEnd)){
                throw new RuntimeException("Conflicto " + request.room() + " ya esta ocupado");
            }
        }

        List<GymClass> instructorClasses = gymClassRepo.findByInstructorIdAndActiveTrue(instructor.getId());
        for (GymClass c : instructorClasses) {
            if (c.getId().equals(id)) continue;
            LocalDateTime cEnd = c.getStartTime().plusMinutes(c.getDurationMinutes());
            if (newStart.isBefore(cEnd) && newEnd.isAfter(c.getStartTime())){
                throw new RuntimeException("Conflicto, El instructor ya tiene otra clase");
            }
        }

        gymClass.setActType(request.actType());
        gymClass.setDescription(request.description());
        gymClass.setCapacity(request.capacity());
        gymClass.setRoom(request.room());
        gymClass.setStartTime(request.startTime());
        gymClass.setDurationMinutes(request.durationMinutes());
        gymClass.setInstructor(instructor);

        GymClass gymClassSaved = gymClassRepo.save(gymClass);

        GymClassResponse gymClassResponse = new GymClassResponse(
                gymClassSaved.getId(),
                gymClassSaved.getActType(),
                gymClassSaved.getDescription(),
                gymClassSaved.getCapacity(),
                gymClassSaved.getRoom(),
                gymClassSaved.getStartTime(),
                gymClassSaved.getStartTime().plusMinutes(gymClassSaved.getDurationMinutes()),
                instructor.getFirstName() + " " + instructor.getLastName()
        );
        return gymClassResponse;
    }

    @Override
    public void deleteClass(Long id) {
        GymClass gymClass = gymClassRepo.findById(id).orElseThrow(
                () -> new RuntimeException("Clase no encontrada")
        );
        gymClass.setActive(false);
        gymClassRepo.save(gymClass);
    }

    @Override
    public GymClassResponse getClass(Long id) {
        GymClass gymClass = gymClassRepo.findById(id).orElseThrow(
                () -> new RuntimeException("Clase no encontrada")
        );

        Instructor instructor = instructorRepo.findById(id).orElseThrow(() ->
                new RuntimeException("Instructor no encontrado"));

        GymClassResponse gymClassResponse = new GymClassResponse(
                gymClass.getId(),
                gymClass.getActType(),
                gymClass.getDescription(),
                gymClass.getCapacity(),
                gymClass.getRoom(),
                gymClass.getStartTime(),
                gymClass.getStartTime().plusMinutes(gymClass.getDurationMinutes()),
                instructor.getFirstName() + " " + instructor.getLastName()
        );
        return gymClassResponse;
    }

    @Override
    public List<GymClassResponse> getAllActiveClasses() {
        return gymClassRepo.findByActiveTrue().stream()
                .map(c -> new GymClassResponse(
                        c.getId(),
                        c.getActType(),
                        c.getDescription(),
                        c.getCapacity(),
                        c.getRoom(),
                        c.getStartTime(),
                        c.getStartTime().plusMinutes(c.getDurationMinutes()),
                        c.getInstructor().getFirstName() + " " + c.getInstructor().getLastName()
                )).toList();
    }

    @Override
    public List<GymClassResponse> getClassesByDate(LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        return gymClassRepo.findByStartTimeBetweenAndActiveTrue(startOfDay, endOfDay).stream()
                .map(c -> new GymClassResponse(
                        c.getId(),
                        c.getActType(),
                        c.getDescription(),
                        c.getCapacity(),
                        c.getRoom(),
                        c.getStartTime(),
                        c.getStartTime().plusMinutes(c.getDurationMinutes()),
                        c.getInstructor().getFirstName() + " " + c.getInstructor().getLastName()
                )).toList();
    }
}
