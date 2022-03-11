
package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true) // 스프링이 제공하는 트랜잭션; true로 두면 성능 최적화
//@AllArgsConstructor
@RequiredArgsConstructor // final이 있는 필드만으로도 생성자를 만들어줌
public class MemberService {

/*
    @Autowired 이렇게만 적으면 혹시라도 바꿔야 할 경우에 문제가 생긴다
*/
    private final MemberRepository memberRepository;
    // 바꿀 일 없는 경우가 대부분이니 final로 설정; 컴파일 시 문제 있으면 확인 가능

    /*
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    요즘은 생성자 injection을 쓰는 것을 권장하며, 생성자가 하나만 있는 경우에는 Autowired가 없어도 스프링이 주입해줌
    +) 맨 위에 @AllArgsConstructor를 명시하면 이것들을 대신할 수 있음(자동 생성)
    */

    /*
            회원 가입
         */
    @Transactional // 쓰기가 필요하므로 readOnly 없이 이곳에만 따로 명시(기본 false)
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId(); // 항상 값이 있을 수 있게 보장됨
    }

    private void validateDuplicateMember(Member member) {
        // EXCEPTION
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 조회만 필요하므로 맨 위에 true로 적은 Transactional이 들어가도록 따로 명시하지 않음
    // 회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    // 회원 단건 조회
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }
}
