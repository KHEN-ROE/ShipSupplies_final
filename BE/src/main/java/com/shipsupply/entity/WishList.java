package com.shipsupply.entity;

import com.shipsupply.dto.AddWishListDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WishList {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Setter
    private String item;

    private String category;

    private String machinery;

    private String currency;

    private Long price;

    @Setter
    private String company;

    private Long leadtime;

    @ManyToOne
    @JoinColumn(name = "userId")
    User user;

    public static WishList addWishList(AddWishListDto addWishListDto, User user) {
        return new WishList(addWishListDto.getId(), addWishListDto.getItem(), addWishListDto.getCategory(), addWishListDto.getMachinery(),
                addWishListDto.getCurrency(), addWishListDto.getPrice(),
                addWishListDto.getCompany(), addWishListDto.getLeadtime(), user
        );
    }

    // 동일성 검증 해야됨
}
