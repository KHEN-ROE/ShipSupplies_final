package com.shipsupply.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
public class CommentDto {

    @NotNull
    private Long id;

    @NotNull
    private String text;

    @NotNull
    private Date date;

    @NotNull
    private Long hitCount = 0L;

    // 작성자
    @NotNull
    private String userId;

    @NotNull
    private Long boardId;

}
