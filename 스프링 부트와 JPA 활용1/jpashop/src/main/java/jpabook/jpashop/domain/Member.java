package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue // GeneratedValue -> 시퀀스 값 사용 시
    @Column(name = "member_id") // 매핑되는 테이블의 pk가 member_id이므로(그냥 두면 Long id 이름 그대로 됨)
    private Long id;

    private String name;

    @Embedded // 내장타입이라는 어노테이션으로 Embeddable과 매핑(둘 중 하나만 있어도 되지만 보통 둘 다 적음)
    private Address address;

    // 읽기 전용
    @OneToMany(mappedBy = "member") // 연관관계의 거울이므로 mappedBy + Order의 member와 매핑된 것을 확인 가능
    // Member쪽에서는 일대다 관계이기 때문에 OneToMany(Order쪽과 반대!)
    private List<Order> orders = new ArrayList<>();
}
