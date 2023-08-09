package com.shipsupply.service;

import com.shipsupply.dto.AddHitDto;
import com.shipsupply.dto.HitDeleteDto;
import com.shipsupply.entity.Board;
import com.shipsupply.entity.Comment;
import com.shipsupply.entity.Hit;
import com.shipsupply.entity.User;
import com.shipsupply.repository.BoardRepository;
import com.shipsupply.repository.CommentRepository;
import com.shipsupply.repository.HitRepository;
import com.shipsupply.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class HitService {

    private final HitRepository hitRepository;

    private final UserRepository userRepository;

    private final BoardRepository boardRepository;

    private final CommentRepository commentRepository;

    public Long getHit(Long commentId) {
        return commentRepository.findById(commentId).get().getHitCount();
    }

    public void addHit(AddHitDto addHitDto) {

        Optional<Hit> findHit = hitRepository.findByUserIdAndCommentId(addHitDto.getUserId(), addHitDto.getCommentId());

        if (findHit.isPresent()) {
            throw new IllegalStateException("좋아요는 한 번만 가능");
        }

        User user = userRepository.findById(addHitDto.getUserId())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원"));

        Board board = boardRepository.findById(addHitDto.getBoardId())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 게시판"));

        Comment comment = commentRepository.findById(addHitDto.getCommentId())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 댓글"));

        Hit hit = Hit.addHit(user, board, comment);
        hitRepository.save(hit);

        comment.setHitCount(comment.getHitCount() + 1);
        commentRepository.save(comment);
    }

    public void deleteHit(HitDeleteDto hitDeleteDto) {
        Comment comment = commentRepository.findById(hitDeleteDto.getCommentId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 댓글"));
        comment.setHitCount(comment.getHitCount() - 1);
    }
}
