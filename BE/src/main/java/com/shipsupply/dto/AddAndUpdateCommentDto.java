package com.shipsupply.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddAndUpdateCommentDto {

    @NotNull
    private String userId;

    @NotNull
    private String text;

    @NotNull
    private Date date;

    @NotNull
    private Long boardId;
}
