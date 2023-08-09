package com.shipsupply.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hit {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId")
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commentId")
    private Comment comment;

    public Hit(User user, Board board, Comment comment) {
        this.user = user;
        this.board = board;
        this.comment = comment;
    }

    public static Hit addHit(User user, Board board, Comment comment) {
        return new Hit(user, board, comment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hit hit)) return false;
        return Objects.equals(getHit(), hit.getHit());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHit());
    }

}
