package com.shipsupply.entity;

import com.shipsupply.common.BaseEntity;
import com.shipsupply.dto.AddAndUpdateCommentDto;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //num 말고 id 쓰는게 관례
    @Setter
    private String text;
    @Setter
    private Date date;
    @Setter
    private Long hitCount = 0L;

    // 작성한 user의 id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    // 어느 게시글에서 쓴 건지
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardId")
    private Board board;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Hit> hits;

    public Comment(String text, Date date, User user, Board board) {
        this.text = text;
        this.date = date;
        this.user = user;
        this.board = board;
    }

    public static Comment addComment(AddAndUpdateCommentDto addCommentDto, User user, Board board) {
        return new Comment(addCommentDto.getText(), addCommentDto.getDate(), user, board);
    }


//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof Comment comment )) return false;
//        return Objects.equals(getId(), comment.getId());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getId());
//    }

}
