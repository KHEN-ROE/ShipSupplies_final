package com.shipsupply.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddWishListDto {

    @NotNull
    private Long id;

    @NotNull
    private String item;

    @NotNull
    private String category;

    @NotNull
    private String machinery;

    @NotNull
    private String currency;

    @NotNull
    private Long price;

    @NotNull
    private String company;

    @NotNull
    private Long leadtime;

    @NotNull
    private String userId;

}
