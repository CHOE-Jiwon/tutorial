package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.respository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {   // test code에서는 함수명을 한글로 지어도 상관 없음! + build 시에 테스트 코드는 포함 안됨
        //given
        Member member = new Member();
        member.setName("spring");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertEquals(member.getName(), findMember.getName());
//        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());        //assertThat 은 junit이 아니라 assertj 에 포함된 메소드

    }

    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring1");

        Member member2 = new Member();
        member2.setName("spring1");


        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertEquals(e.getMessage(), "이미 존재하는 회원입니다.");

//        memberService.join(member2); //try catch 로 할 수도 있음.
        /*try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e){
            assertEquals(e.getMessage(), "이미 존재하는 회원입니다.");
        }*/

        //then
    }


    @Test
    void 회원조회() {
    }

    @Test
    void findOne() {
    }
}