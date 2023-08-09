package com.shipsupply.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddHitDto {

    @NotNull
    private String userId;

    @NotNull
    private Long boardId;

    @NotNull
    private Long commentId;

}
