package com.shipsupply.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shipsupply.entity.Comment;
import com.shipsupply.entity.Hit;
import com.shipsupply.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
public class BoardDetailDto {

    @NotNull
    private Long id;

    @NotNull
    private String title;

    @NotNull
    private String text;

    @NotNull
    private String userId;

    @NotNull
    private Date date;

    @NotNull
    private List<CommentDto> comments;

    //    private List<Long> hits;
}
