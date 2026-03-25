package com.gym.api.services.impl;

import com.gym.api.dtos.MemberRequest;
import com.gym.api.dtos.MemberResponse;
import com.gym.api.entity.Member;
import com.gym.api.repository.BookingRepository;
import com.gym.api.repository.GymClassRepository;
import com.gym.api.repository.MemberRepository;
import com.gym.api.services.BookingService;
import com.gym.api.services.MemberService;
import org.springframework.context.annotation.Lazy;

import java.util.List;

public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final BookingRepository bookingRepository;
    private final GymClassRepository gymClassRepository;
    private final BookingService bookingService;

    public MemberServiceImpl(MemberRepository memberRepository, BookingRepository bookingRepository, GymClassRepository gymClassRepository, @Lazy  BookingService bookingService) {
        this.memberRepository = memberRepository;
        this.bookingRepository = bookingRepository;
        this.gymClassRepository = gymClassRepository;
        this.bookingService = bookingService;
    }

    @Override
    public MemberResponse createMember(MemberRequest memberRequest) {
        if (memberRepository.existsByEmail(memberRequest.email())){
            throw new RuntimeException("Este email ya esta registrado");
        }

        Member member = new Member();
        member.setName(memberRequest.name());
        member.setLastname(memberRequest.lastname());
        member.setEmail(memberRequest.email());
        member.setAddress(memberRequest.address());
        member.setPhone(memberRequest.phone());

        member.setActive(true);

        Member createdMember = memberRepository.save(member);

        MemberResponse memberRes = new MemberResponse(
                createdMember.getId(),
                createdMember.getName(),
                createdMember.getLastname(),
                createdMember.getEmail(),
                createdMember.getAddress(),
                createdMember.getPhone()
        );
        return memberRes;
    }

    @Override
    public MemberResponse updateMember(Long id, MemberRequest member) {
        return null;
    }

    @Override
    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Miembro no encontrado")
        );

        bookingService.cancellAllFuturesBookings(id);
        member.setActive(false);
        memberRepository.save(member);
    }

    @Override
    public MemberResponse findMemberByEmail(String email) {
        return null;
    }

    @Override
    public List<MemberResponse> findAllActiveMembers() {
        return List.of();
    }
}
