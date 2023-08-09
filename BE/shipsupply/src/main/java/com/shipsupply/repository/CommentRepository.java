package com.shipsupply.repository;

import com.shipsupply.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBoardIdOrderByHitCountDesc(Long BoardId);
}
