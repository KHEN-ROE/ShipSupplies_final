package com.shipsupply.controller;

import com.shipsupply.dto.AddWishListDto;
import com.shipsupply.dto.DeleteWishListDto;
import com.shipsupply.dto.WishListDto;
import com.shipsupply.entity.WishList;
import com.shipsupply.service.WishListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/wish")
@RequiredArgsConstructor
public class WishListController {

    private final WishListService wishListService;

    @Operation(summary = "관심상품 조회")
    @ApiResponse(responseCode = "200", description = "관심상품 정보가 정상적으로 조회되었습니다.")
    @GetMapping("/get")
    public List<WishListDto> getList(@RequestParam String userId) {
        return wishListService.getList(userId);
    }

    @Operation(summary = "관심상품 추가")
    @ApiResponse(responseCode = "200", description = "관심상품이 정상적으로 추가되었습니다.")
    @PostMapping("/add")
    public void addList(@RequestBody @Valid AddWishListDto addWishListDto) {
        wishListService.addList(addWishListDto);
    }

    @Operation(summary = "관심상품 삭제")
    @ApiResponse(responseCode = "200", description = "관심상품이 정상적으로 삭제되었습니다.")
    @DeleteMapping("/delete")
    public void deleteList(@RequestBody @Valid DeleteWishListDto deleteWishListDto) {
        wishListService.deleteList(deleteWishListDto);
    }

}
