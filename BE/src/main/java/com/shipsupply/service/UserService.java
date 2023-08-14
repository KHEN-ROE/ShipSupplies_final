package com.shipsupply.service;

import com.shipsupply.config.PasswordEncrypter;
import com.shipsupply.dto.*;
import com.shipsupply.entity.User;
import com.shipsupply.exception.DeletedUserException;
import com.shipsupply.exception.EmailDuplicateException;
import com.shipsupply.exception.UserDuplicateException;
import com.shipsupply.exception.WrongPasswordException;
import com.shipsupply.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncrypter passwordEncrypter;

    public void join(CreateUserDto userDto) {

        userRepository.findById(userDto.getId()).ifPresent(user -> {
            throw new UserDuplicateException("이미 존재하는 회원");
        });

        userRepository.findByEmail(userDto.getEmail()).ifPresent(user -> {
            throw new EmailDuplicateException("중복된 이메일");
        });

        // 비밀번호 암호화
        String encryptedPassword = PasswordEncrypter.encrypt(userDto.getPassword());
        User user = User.createUser(userDto, encryptedPassword);
        userRepository.save(user);
    }

    public LoginResponseDto login(LoginDto loginDto, HttpServletRequest request) {

        User findUser = userRepository.findById(loginDto.getId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 회원"));
        if (findUser.isDeleted()) {
            throw new DeletedUserException("탈퇴한 회원");
        }

        // 암호화
        String encryptedPassword = PasswordEncrypter.encrypt(loginDto.getPassword());

        if (encryptedPassword.equals(findUser.getPassword())) {
            // 로그인 성공시 쿠키에 JSESSIONID(세션을 식별할 수 있는 id) 저장. 즉, 쿠키에 값을 리턴하는 로직 없어도 세션id가 쿠키 형태로 저장됨.
            LoginResponseDto loginResponseDto = new LoginResponseDto(findUser.getId(), findUser.getUsername(), findUser.getEmail());
            // 세션에 사용자 ID 저장
            HttpSession session = request.getSession();
            session.setAttribute("loginUser", loginResponseDto.getUserId());

            return loginResponseDto;
        } else {
            throw new WrongPasswordException("비밀번호 불일치");
        }
    }

    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (session != null) {
            session.invalidate();
        }
    }

    public String confirmPassword(ConfirmPwdDto confirmPwdDto) {

        User findUser = userRepository.findById(confirmPwdDto.getId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 사용자"));

        String encryptedPassword = PasswordEncrypter.encrypt(confirmPwdDto.getPassword());

        if (encryptedPassword.equals(findUser.getPassword())) {
            if (confirmPwdDto.getPassword().equals(confirmPwdDto.getConfirmPassword())) {
                return "pass";
            } else {
                throw new IllegalStateException("비밀번호 불일치");
            }
        } else {
            throw new IllegalStateException("권한 없음");
        }

    }

    public void update(UpdateUserDto updateUserDto) {

        User findUser = userRepository.findById(updateUserDto.getId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 사용자"));

        String encryptedPassword = PasswordEncrypter.encrypt(updateUserDto.getPassword());

        if (encryptedPassword.equals(findUser.getPassword())) {
            findUser.setPassword(PasswordEncrypter.encrypt(updateUserDto.getNewPassword()));
            findUser.setEmail(updateUserDto.getEmail());
            findUser.setUsername(updateUserDto.getUsername());
        } else {
            throw new IllegalStateException("비밀번호 불일치");
        }
    }

    public void delete(ConfirmPwdDto confirmPwdDto) {
        User findUser = userRepository.findById(confirmPwdDto.getId()).orElseThrow(() -> new IllegalStateException("존재하지 않는 회원"));

        String encryptedPassword = PasswordEncrypter.encrypt(confirmPwdDto.getPassword());

        if (confirmPwdDto.getPassword().equals(confirmPwdDto.getConfirmPassword())) {
            if (encryptedPassword.equals(findUser.getPassword())) {
                findUser.setDeleted(true);
            } else {
                throw new IllegalStateException("db 비밀번호 불일치");
            }
        } else {
            throw new IllegalStateException("입력값 불일치");
        }

    }

}
