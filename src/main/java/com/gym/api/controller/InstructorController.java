package com.gym.api.controller;

import com.gym.api.dtos.InstructorRequest;
import com.gym.api.dtos.InstructorResponse;
import com.gym.api.services.InstructorService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("instructor")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @GetMapping("/{id}")
    ResponseEntity<InstructorResponse> getInstructor(@PathVariable Long id){
        return ResponseEntity.ok(instructorService.getInstructor(id));
    }

    @GetMapping
    ResponseEntity<List<InstructorResponse>> getAllInstructors(){
        return ResponseEntity.ok(instructorService.getAllActiveInstructor());
    }

    @PostMapping
    public ResponseEntity<InstructorResponse> createInstructor(@Valid @RequestBody InstructorRequest request){
        return ResponseEntity.ok(instructorService.createInstructor(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InstructorResponse> updateInstructor(@PathVariable Long id, @Valid @RequestBody InstructorRequest request){
        return ResponseEntity.ok(instructorService.updateInstructor(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<InstructorResponse> deleteInstructor(@PathVariable Long id){
        instructorService.deleteInstructor(id);
        return ResponseEntity.noContent().build();
    }

}
