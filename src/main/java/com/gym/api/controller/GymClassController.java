package com.gym.api.controller;

import com.gym.api.dtos.GymClassRequest;
import com.gym.api.dtos.GymClassResponse;
import com.gym.api.entity.GymClass;
import com.gym.api.services.GymClassService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/classes")
public class GymClassController {

    private final GymClassService gymClassService;

    public GymClassController(GymClassService gymClassService) {
        this.gymClassService = gymClassService;
    }

    @PostMapping
    public ResponseEntity<GymClassResponse> createClass(@RequestBody GymClassRequest request) {
        return ResponseEntity.status(201).body(gymClassService.createClass(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GymClassResponse> updateClass(@PathVariable Long id, @RequestBody GymClassRequest request) {
        return ResponseEntity.ok(gymClassService.updateClass(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GymClassResponse> getClass(@PathVariable Long id) {
        return ResponseEntity.ok(gymClassService.getClass(id));
    }

    @GetMapping
    public ResponseEntity<List<GymClassResponse>> getAllClasses() {
        return ResponseEntity.ok(gymClassService.getAllActiveClasses());
    }

    @GetMapping("/calendar")
    public ResponseEntity<List<GymClassResponse>> getClassesByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate date) {
        return ResponseEntity.ok(gymClassService.getClassesByDate(date));
    }

    @DeleteMapping
    public ResponseEntity<GymClassResponse> delete(@PathVariable Long id) {
        gymClassService.deleteClass(id);
        return ResponseEntity.noContent().build();
    }
}
