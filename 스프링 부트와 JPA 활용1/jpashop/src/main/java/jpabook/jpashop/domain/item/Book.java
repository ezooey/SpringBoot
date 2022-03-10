package jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B") // 임의로 구분값 넣을 수 있음
@Getter @Setter
public class Book extends Item {

    private String author;
    private String isbn;
}
