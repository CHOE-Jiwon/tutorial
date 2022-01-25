package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.respository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// @Service -> @Component 를 가지고 있음.
//@Service
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
        // 같은 이름 중복 X
        // Optional을 바로 반환하는게 좋지는 않음. (안이쁨)
//        Optional<Member> result = memberRepository.findByName(member.getName());
        // 이건 리팩토링으로 메소드로 빼버리면 좋음.
        validateDuplicateMember(member);

        // get을 사용해서 바로 꺼낼 수 있긴 한데, 권장하진 않는 방법.
        /*result.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });*/

        memberRepository.save(member);
        return member.getId();
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
