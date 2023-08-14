package com.shipsupply.entity;

import com.shipsupply.common.BaseEntity;
import com.shipsupply.dto.CreateUserDto;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class User extends BaseEntity {

    @Id
    @Column(name = "userId")
    @Setter
    private String id;
    @Setter
    private String password;
    @Setter
    private String newPassword;

    private String confirmPassword;

    @Setter
    private String email;
    @Setter
    private String username;
    @Setter
    private String role;

    @Setter
    private boolean deleted = false; // 유저 논리적 삭제(삭제된 것 처럼 보이지만 db에서 삭제X)를 위한 필드
                                     // 반대는 물리적 삭제(db에서 실제로 삭제)

    public User(String id, String password, String email, String username, String role) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.username = username;
        this.role = role;
    }

    public static User createUser(CreateUserDto createUserDto, String password) {
        return new User(createUserDto.getId(), password, createUserDto.getEmail(), createUserDto.getUsername(), createUserDto.getRole());
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof User user)) return false;
//        return Objects.equals(getId(), user.getId());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getId());
//    }

}
