package com.shipsupply.controller;

import com.shipsupply.dto.*;
import com.shipsupply.entity.User;
import com.shipsupply.repository.UserRepository;
import com.shipsupply.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // ResponseEntity는 스프링 프레임워크에서 제공하는 클래스. http 응답의 상태코드, 헤더, 본문 등을 포함하는 정보를 갖는 컨테이너
    //이 클래스를 사용하면 컨트롤러에서 http 응답을 상세하게 조작가능
    //http 요청에 대한 응답을 생성하는 역할

    @Operation(summary = "회원가입")
    @ApiResponse(responseCode = "200", description = "회원가입에 성공했습니다.")
    @PostMapping("/join")
    public void join(@RequestBody @Valid CreateUserDto createUserDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.info("error={}", bindingResult.getFieldError());
        }
            userService.join(createUserDto);
    }

    @Operation(summary = "사용자 로그인")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "사용자가 정상적으로 로그인되었습니다."))
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody @Valid LoginDto loginDto, HttpServletRequest request) {
        log.info("로그인 컨트롤러 호출");
        return ResponseEntity.ok().body(userService.login(loginDto, request));
    }

    @Operation(summary = "사용자 로그아웃")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "사용자가 정상적으로 로그아웃되었습니다."))
    @PostMapping("logout")
    public void logout(HttpServletRequest request) {
        log.info("logout 호출");
        userService.logout(request);
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirmPassword(@RequestBody @Valid ConfirmPwdDto confirmPwdDto) {
        return ResponseEntity.ok().body(userService.confirmPassword(confirmPwdDto));
    }

    @Operation(summary = "회원정보 수정")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "사용자 정보가 정상적으로 업데이트되었습니다."))
    @PatchMapping("/update")
    public void update(@RequestBody @Valid UpdateUserDto userDto) {
        userService.update(userDto);
    }

    @Operation(summary = "회원정보 삭제")
    @ApiResponses(@ApiResponse(responseCode = "200", description = "사용자 정보가 정상적으로 삭제되었습니다."))
    @DeleteMapping("/delete")
    public void delete(@RequestBody @Valid ConfirmPwdDto confirmPwdDto) {
        userService.delete(confirmPwdDto);
    }
}
