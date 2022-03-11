package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    // Order와 Member는 다대일 관계이므로
    @JoinColumn(name = "member_id")
    private Member member; // 주문 회원

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    // cascade를 두면 각각 persist(orderItemA) 등등을 할 필요 없이 persist(order) 하나만 해주면 된다
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery; // 배송 정보

    // private Date date; 썼어야 했던 것을 LocalDateTime으로 자동으로 지원됨
    private LocalDateTime orderDate; // 주문 시간
    //하이버네이트 매핑 전략으로 인해 db에서는 order_date로 바뀜

    @Enumerated(EnumType.STRING)
    private OrderStatus status; // 주문 상태 [ORDER, CANCEL]

    // == 연관관계 메소드==//
    // 핵심적으로 동작하는 곳에 이 메소드를 적는 것이 권장됨
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
        /*
        public static void main(String[] args) {
        Member member = new Member();
        Order order = new Order();
        member.getOrders().add(order); 라고 적어야 했던 것을 축약 가능
         */
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }
}
