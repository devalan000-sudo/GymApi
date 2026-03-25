package com.gym.api.services;

import com.gym.api.dtos.MemberRequest;
import com.gym.api.dtos.MemberResponse;

import java.util.List;

public interface MemberService {
    MemberResponse createMember(MemberRequest member);
    MemberResponse updateMember(Long id, MemberRequest member);
    void deleteMember(Long id);
    MemberResponse findMemberByEmail(String email);
    List<MemberResponse> findAllActiveMembers();
}
