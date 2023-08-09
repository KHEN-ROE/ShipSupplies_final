package com.shipsupply.controller;

import com.shipsupply.dto.AddHitDto;
import com.shipsupply.dto.HitDeleteDto;
import com.shipsupply.entity.Hit;
import com.shipsupply.service.HitService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/hit")
@RequiredArgsConstructor
public class HitController {

    private final HitService hitService;

    @Operation(summary = "좋아요 개수 보기")
    @ApiResponse(responseCode = "200", description = "좋아요 개수가 정상적으로 조회되었습니다.")
    @GetMapping("/get/{commentId}")
    public Long getHit(@PathVariable Long commentId) {
        return hitService.getHit(commentId);
    }

    @Operation(summary = "좋아요 추가")
    @ApiResponse(responseCode = "200", description = "좋아요가 정상적으로 추가되었습니다.")
    @PostMapping("/add")
    public void addHit(@RequestBody @Valid AddHitDto addHitDto) {
        log.info("받은 hit 정보 : " + addHitDto);
        hitService.addHit(addHitDto);
    }

    @Operation(summary = "좋아요 취소")
    @ApiResponse(responseCode = "200", description = "좋아요가 정상적으로 취소되었습니다.")
    @DeleteMapping("/delete")
    public void deleteHit(@RequestBody @Valid HitDeleteDto hitDeleteDto) {
        hitService.deleteHit(hitDeleteDto);
    }
}
