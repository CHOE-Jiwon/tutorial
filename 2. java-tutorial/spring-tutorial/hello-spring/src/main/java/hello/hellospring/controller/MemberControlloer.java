package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/*
 * Spring container가 뜰 때, Controller 어노테이이 있으면 Membercontroller 객체를 생성해서 container에 담아둔다. 이걸 Spring가 관리한다.
 * -> Spring Bean이 관리된다.
 * */
@Controller
public class MemberControlloer {

    //    private final MemberService memberService = new MemberService();  얘는 인스턴스가 많이 생성될 필요가 없는 친구다.
    private final MemberService memberService;

    /*
     * Autowired -> Spring Container에 있는 memberService를 연결시켜줌
     * 근데 memberService가 Spring Container에 떠있냐? 아니다.
     * MemberService 에 Service annotation을 붙여주면 됨.
     *
     * DI에는 필드 주입, setter 주입, 생성자 주입이 있는데 -> 생성자 주입이 제일 좋음.
     * */
    @Autowired
    public MemberControlloer(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/members/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);

        return "members/memberList";
    }
}
