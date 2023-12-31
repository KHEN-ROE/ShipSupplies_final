package com.shipsupply.controller;

import com.shipsupply.dto.*;
import com.shipsupply.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Slf4j
@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // Page<Board> -> Page 객체로 감싸서 반환하면,
    // 클라이언트에서는 해당 페이지의 게시글 데이터뿐만 아니라 전체 페이지 수, 현재 페이지 번호 등의 페이지네이션 정보도 함께 받을 수 있음
    // 클라이언트의 요청에 따른 페이지네이션 처리를 위해 Pageable을 활용
    @Operation(summary = "게시글 목록 보기")
    @ApiResponse(responseCode = "200", description = "게시글 목록이 정상적으로 조회되었습니다.")
    @GetMapping("/view")
    public Page<BoardListDto> getList(@RequestParam int page, @RequestParam int size) { // 요청된 페이지 번호, 한 페이지에 보여줄 게시글 개수
        log.info("page : " + page + "," + "size : " + size);
        Pageable pageable = PageRequest.of(page, size); //page와 size를 기반으로 한 Pageable 객체 생성
        return boardService.getList(pageable);
    }

    @Operation(summary = "게시글 상세 보기")
    @ApiResponse(responseCode = "200", description = "게시글 상세 정보가 정상적으로 조회되었습니다.")
    @GetMapping("/view/{id}")
    public BoardDetailDto getBoard(@PathVariable Long id) {
        log.info("글 번호 : " + id);
        return boardService.getBoard(id);
    }

    @Operation(summary = "게시글 작성")
    @ApiResponse(responseCode = "200", description = "게시글이 정상적으로 작성되었습니다.")
    @PostMapping("/add")
    public void addBoard(@RequestBody @Valid AddBoardDto addBoardDto) {
        log.info("받은 게시글 정보 : {}", addBoardDto);
        boardService.addBoard(addBoardDto);
    }

    @Operation(summary = "게시글 수정")
    @ApiResponse(responseCode = "200", description = "게시글이 정상적으로 수정되었습니다.")
    @PatchMapping("/update/{id}")
    public void updateBoard(@PathVariable Long id, @RequestBody @Valid UpdateBoardDto updateBoardDto) {
        log.info("받은 정보 : " + id + "," + updateBoardDto);
        boardService.updateBoard(id, updateBoardDto);
    }

    @Operation(summary = "게시글 삭제")
    @ApiResponse(responseCode = "200", description = "게시글이 정상적으로 삭제되었습니다.")
    @DeleteMapping("/delete/{id}")
    public void deleteBoard(@PathVariable Long id, @RequestBody @Valid DeleteBoardDto deleteBoardDto) {
        boardService.deleteBoard(id, deleteBoardDto);
    }
}
