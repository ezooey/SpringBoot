package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

//    @PersistenceContext
    // 이 어노테이션으로 스프링이 엔티티매니저를 주입해줌
    // 대신 final로 바꾼 뒤 RequiredArgsConstructor로 대체
    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) { // 단건 조회
        return em.find(Member.class, id);
    }

    public List<Member> findAll() { // 모두 조회
        // JPQL은 테이블이 아닌 객체를 대상으로 쿼리를 짠다.
        // 하단의 쿼리는 객체 Member의 별칭을 m으로 두고 Member 엔티티를 조회한다는 뜻
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) { // 이름으로 조회
        // :name은 파라미터를 바인딩한 것
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

}
