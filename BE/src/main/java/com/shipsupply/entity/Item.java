package com.shipsupply.entity;

import com.shipsupply.common.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseEntity {

    @Id //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String item;
    private String assembly;
    private String company;
    private String category;
    private Long leadtime;
    private String machinery;
    private String partNo1;
    private String partNo2;
    private String currency;
    private String ship;
    private String subject;
    private Long price;
    private Date date;

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof Item item)) return false;
//        return Objects.equals(getId(), item.getId());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getId());
//    }

}