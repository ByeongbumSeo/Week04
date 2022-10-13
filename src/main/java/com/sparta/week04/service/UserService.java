package com.sparta.week04.service;

import com.sparta.week04.dto.LoginRequestDto;
import com.sparta.week04.dto.SignupRequestDto;
import com.sparta.week04.dto.TokenDto;
import com.sparta.week04.dto.UserResponseDto;
import com.sparta.week04.entity.RefreshToken;
import com.sparta.week04.entity.User;
import com.sparta.week04.repository.RefreshTokenRepository;
import com.sparta.week04.repository.UserRepository;
import com.sparta.week04.security.provider.JwtAuthProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtAuthProvider jwtAuthProvider;

    // 회원가입
    public UserResponseDto registerUser(SignupRequestDto signupRequestDto) throws IllegalAccessException {
        String nickname = signupRequestDto.getNickname();
        String password = signupRequestDto.getPassword();
        String passwordConfirm = signupRequestDto.getPasswordConfirm();

        Optional<User> found = userRepository.findByNickname(nickname);
        if (found.isPresent()) {
            throw new IllegalAccessException("중복 닉네임 확인!");
        }
        if (!password.equals(passwordConfirm)) {
            throw new IllegalAccessException("비밀번호가 서로 다릅니다!");
        }
        // 패스워드 암호화
        password = passwordEncoder.encode(signupRequestDto.getPassword());
        LoginRequestDto dto = LoginRequestDto.builder()
                .nickname(nickname)
                .password(password)
                .build();

        User user = new User(dto);
        userRepository.save(user);

        UserResponseDto responseDto = new UserResponseDto(user);

        return responseDto;
//        return user.getNickname() + " 가입완료!";
    }



    // 로그인
    @Transactional
    public UserResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
//        Optional <User> id = userRepository.findByNickname(loginRequestDto.getNickname());
//        if(!id.get().getPassword().equals(loginRequestDto.getPassword())){
//            return Optional.empty();
//        }
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginRequestDto.getNickname(), loginRequestDto.getPassword());

        // 2. 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보를 기반으로 JWT 토큰 생성
        TokenDto tokenDto = jwtAuthProvider.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        response.addHeader("Access-Token", tokenDto.getGrantType()+" "+tokenDto.getAccessToken());
        response.addHeader("Refresh-Token", tokenDto.getRefreshToken());
        // userRequestDto.getUsername() 의 값과 동일한 userName의 모든 정보 불러오기
        // 로그인시 user 정보 뿌려주고싶음
        User user = userRepository.findByNickname(loginRequestDto.getNickname())
                .orElseThrow( () -> new UsernameNotFoundException("유저를 찾을 수 없습니다"));

        UserResponseDto userResponseDto = new UserResponseDto(user);

        return userResponseDto;
    }
}