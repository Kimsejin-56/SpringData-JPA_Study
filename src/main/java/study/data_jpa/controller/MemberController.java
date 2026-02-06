package study.data_jpa.controller;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import study.data_jpa.entity.Member;
import study.data_jpa.repository.MemberRepository;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")// 도메인 클래스 컨버터 사용 전
    public String findMember(@PathVariable("id") Long id) {
        Member member = memberRepository.findById(id).get();
        return member.getUsername();
    }

    /**
     * HTTP 요청은 회원 id 를 받지만 도메인 클래스 컨버터가 중간에 동작해서 회원 엔티티 객체를 반환
     * 도메인 클래스 컨버터도 리파지토리를 사용해서 엔티티를 찾음
     *  - 조회용으로만 사용 (애초에 권장 X)
     */
    @GetMapping("/members2/{id}")// 도메인 클래스 컨버터 사용후
    public String findMember2(@PathVariable("id") Member member) {
        return member.getUsername();
    }

    @PostConstruct
    public void init() {
        memberRepository.save(new Member("UserA"));
    }
}
