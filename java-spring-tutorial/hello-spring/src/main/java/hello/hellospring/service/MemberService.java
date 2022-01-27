package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.respository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

// @Service -> @Component 를 가지고 있음.
//@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;    // black final variable : https://makemethink.tistory.com/184

    /*
    * DI (Dependency Injection)
    * */
//    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /*
    * 회원 가입
    * */
    public Long join(Member member) {

        /*
        * 시간 측정 코드 삽입.
        * 이런걸 각 메소드마다 넣어줘야 된다면...? 메소드가 1000개라면...?
        * 이럴 때 사용할 수 있는 개념이 AOP(Aspect Oriented Programming)
        * 공통 관심 사항 | 핵심 관심 사항
        * */
//        long start = System.currentTimeMillis();
//
//        try {
            validateDuplicateMember(member);
            memberRepository.save(member);
            return member.getId();
//        } finally {
//            long finish = System.currentTimeMillis();
//            long timeMs = finish - start;
//            System.out.println("join = " + timeMs + "ms");
//        }

        // 같은 이름 중복 X
        // Optional을 바로 반환하는게 좋지는 않음. (안이쁨)
//        Optional<Member> result = memberRepository.findByName(member.getName());
        // 이건 리팩토링으로 메소드로 빼버리면 좋음.
//        validateDuplicateMember(member);

        // get을 사용해서 바로 꺼낼 수 있긴 한데, 권장하진 않는 방법.
        /*result.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });*/

//        memberRepository.save(member);
//        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName()).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    /*
    * 전체 회원 조회
    * */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /*
    * 회원 찾기
    * */
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

}
