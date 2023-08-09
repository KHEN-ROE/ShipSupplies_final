package com.shipsupply.service;

import com.shipsupply.dto.AddAndUpdateCommentDto;
import com.shipsupply.dto.CommentDto;
import com.shipsupply.dto.DeleteCommentDto;
import com.shipsupply.entity.Board;
import com.shipsupply.entity.Comment;
import com.shipsupply.entity.User;
import com.shipsupply.repository.BoardRepository;
import com.shipsupply.repository.CommentRepository;
import com.shipsupply.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;

    private final UserRepository userRepository;

    private final BoardRepository boardRepository;

    // 댓글은 모든 사람이 다 볼 수 있어야 함. 근데 비밀 댓글인 경우?(이건 나중에) + 대댓글은?
    public List<CommentDto> getComment(Long boardId) {
        List<Comment> findAll = commentRepository.findAllByBoardIdOrderByHitCountDesc(boardId);
        return findAll.stream()
                .map(comment -> new CommentDto(comment.getId(), comment.getText(), comment.getDate(), comment.getHitCount(), comment.getUser().getId(), comment.getBoard().getId()))
                .collect(Collectors.toList());
    }

    public void addComment(AddAndUpdateCommentDto addCommentDto) {
        // 댓글 작성자가 db에 있는 회원인지 확인
        User findUser = userRepository.findById(addCommentDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원"));
        Board findBoard = boardRepository.findById(addCommentDto.getBoardId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글"));
        Comment comment = Comment.addComment(addCommentDto, findUser, findBoard);
        commentRepository.save(comment);
    }

    public void updateComment(Long id, AddAndUpdateCommentDto updateCommentDto) {

        Board findBoard = boardRepository.findById(updateCommentDto.getBoardId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 게시글"));
        Comment findComment = commentRepository.findById(id).orElseThrow(() -> new IllegalStateException("존재하지 않는 댓글"));

        if (findComment.getUser().getId().equals(updateCommentDto.getUserId())) {
            findComment.setText(updateCommentDto.getText());
            findComment.setDate(updateCommentDto.getDate());
        } else {
            throw new IllegalStateException("일치하지 않는 사용자");
        }

    }

    public void deleteComment(Long id, DeleteCommentDto deleteCommentDto) {

        Board findBoard = boardRepository.findById(deleteCommentDto.getBoardId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 게시글"));
        Comment findComment = commentRepository.findById(id).orElseThrow(() -> new IllegalStateException("존재하지 않는 댓글"));

        if (findComment.getUser().getId().equals(deleteCommentDto.getUserId())) {
            commentRepository.deleteById(id);
        } else {
            throw new IllegalStateException("일치하지 않는 사용자");
        } 
    }
}
