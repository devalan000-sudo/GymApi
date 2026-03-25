package com.gym.api.controller;

import com.gym.api.dtos.MemberRequest;
import com.gym.api.dtos.MemberResponse;
import com.gym.api.entity.Member;
import com.gym.api.services.MemberService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }



    @PostMapping
    public ResponseEntity<MemberResponse> createMember(@Valid @RequestBody MemberRequest memberRequest) {
        return ResponseEntity.ok(memberService.createMember(memberRequest));
    }

    @PutMapping
    public ResponseEntity<MemberResponse> update (@PathVariable Long id, @Valid @RequestBody MemberRequest memberRequest) {
        return ResponseEntity.ok(memberService.updateMember(id, memberRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }
}
