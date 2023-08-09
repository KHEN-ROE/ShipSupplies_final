package com.shipsupply.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemListDto {

    @NotNull
    private Long id;

    @NotNull
    private String item;

    @NotNull
    private String machinery;

    @NotNull
    private String assembly;

    @NotNull
    private String partNo1;

    @NotNull
    private String category;

    @NotNull
    private String company;

    @NotNull
    private String currency;

    @NotNull
    private Long price;

    @NotNull
    private String ship;

    @NotNull
    private String subject;

}
