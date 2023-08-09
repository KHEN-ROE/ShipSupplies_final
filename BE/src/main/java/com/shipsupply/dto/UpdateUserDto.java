package com.shipsupply.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {

    @NotNull
    private String id;

    @NotNull
    private String password;

    @NotNull
    private String newPassword;

    @NotNull
    private String email;

    @NotNull
    private String username;


}
