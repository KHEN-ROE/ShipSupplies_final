package com.shipsupply.service;

import com.shipsupply.dto.*;
import com.shipsupply.entity.Board;
import com.shipsupply.entity.User;
import com.shipsupply.repository.BoardRepository;
import com.shipsupply.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final CommentService commentService;

    public Page<BoardListDto> getList(Pageable pageable) {
        // BoardDto를 Board로 변환
        Page<Board> findAll = boardRepository.findAll(pageable);
        // 엔티티 -> dto 변환
        List<BoardListDto> collect = findAll.stream()
                .map(b -> new BoardListDto(b.getId(), b.getTitle(), b.getUser().getId(), b.getDate()))
                .collect(Collectors.toList());

        return new PageImpl<>(collect, pageable, findAll.getTotalElements());

    }

    public BoardDetailDto getBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new IllegalStateException("존재하지 않는 게시글"));

        // 필요한 정보를 BoardDetailDto에 담기
        String title = board.getTitle();
        String text = board.getText();
        String userId = board.getUser().getId();
        Date date = board.getDate();
        //comment를 추가하는데, 별도의 commentDto를 만들어야 함
        List<CommentDto> comments = commentService.getComment(id);

        return new BoardDetailDto(id, title, text, userId, date, comments);

    }

    public void addBoard(AddBoardDto addBoardDto) {

        User findUser = userRepository.findById(addBoardDto.getUserId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 유저"));
        Board board = Board.addBoard(addBoardDto, findUser);

        boardRepository.save(board);

    }

    public void updateBoard(Long id, UpdateBoardDto updateBoardDto) {
        Board findBoard = boardRepository.findById(id).orElseThrow(() -> new IllegalStateException("존재하지 않는 게시글"));

        if (!findBoard.getUser().getId().equals(updateBoardDto.getUserId())) {
            throw new IllegalArgumentException("일치하지 않는 사용자");
        }

        findBoard.setTitle(updateBoardDto.getTitle());
        findBoard.setText(updateBoardDto.getText());

    }

    public void deleteBoard(Long id, DeleteBoardDto deleteBoardDto) {

        Board findBoard = boardRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글"));

        if (!findBoard.getUser().getId().equals(deleteBoardDto.getUserId())) {
            throw new IllegalArgumentException("일치하지 않는 사용자");
        }

        boardRepository.deleteById(id);

    }
}
