package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {
                        //@Valid: @NotEmpty로 지정한 것의 유효성 검사
                                                // BindingResult: 오류가 담긴 채로 실행
        if(result.hasErrors()) {
            return "members/createMemberForm";
        }
        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
//        List<Member> members = memberService.findMembers();
//        model.addAttribute("members", members);
//        ctrl + alt + N 으로 inline 하여 아래 한 줄로 합칠 수 있지만 상황에 따라 구분해 사용할 것.
//        +) 보통은 dto를 넘기는 것이 일반적이지만 단순히 예시를 보이기 위해 엔티티(Member)를 그냥 넘겼음
        model.addAttribute("members", memberService.findMembers());
        return "members/memberList";
    }
}
