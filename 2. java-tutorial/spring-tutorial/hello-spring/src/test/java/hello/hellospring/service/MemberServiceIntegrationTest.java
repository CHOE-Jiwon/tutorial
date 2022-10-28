package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.respository.MemberRepository;
import hello.hellospring.respository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional // Aftereach 이런거 안쓰게 해줌. -> `testcase에 달면` 테스트할때 트랜잭션을 실행하고 테스트가 끝나면 롤백이 됨. (실제 DB에 반영이 안된다는 것.)
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
//    @Commit 해당 어노테이션을 사용하면 트랜잭션이 커밋까지 됨.
    void 회원가입() {   // test code에서는 함수명을 한글로 지어도 상관 없음! + build 시에 테스트 코드는 포함 안됨
        //given
        Member member = new Member();
        member.setName("spring120");

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