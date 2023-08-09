package com.shipsupply.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
public class BoardListDto {

    @NotNull
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String userId; // userId

    @NotNull
    private Date date;

}
