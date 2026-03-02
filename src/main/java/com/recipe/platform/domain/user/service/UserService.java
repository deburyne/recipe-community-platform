package com.recipe.platform.domain.user.service;

/*import com.recipe.platform.domain.user.User;
import com.recipe.platform.domain.user.UserRepository;
import com.recipe.platform.domain.user.dto.LoginRequest;
import com.recipe.platform.domain.user.dto.LoginResponse;
import com.recipe.platform.domain.user.dto.SignupRequest;
import com.recipe.platform.domain.user.dto.UserResponse;
import com.recipe.platform.global.exception.DuplicateEmailException;
import com.recipe.platform.global.exception.InvalidCredentialsException;
import com.recipe.platform.global.exception.UserNotFoundException;
import com.recipe.platform.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
*/
import com.recipe.platform.domain.user.User;
import com.recipe.platform.domain.user.UserRepository;
import com.recipe.platform.domain.user.dto.LoginRequest;
import com.recipe.platform.domain.user.dto.LoginResponse;
import com.recipe.platform.domain.user.dto.SignupRequest;
import com.recipe.platform.domain.user.dto.UserResponse;
import com.recipe.platform.global.exception.DuplicateEmailException;
import com.recipe.platform.global.exception.InvalidCredentialsException;
import com.recipe.platform.global.exception.UserNotFoundException;
import com.recipe.platform.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    public UserResponse signup(SignupRequest request) {
        //이메일 중복체크
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateEmailException("이미 사용중인 이메일 입니다");
        }
        //닉네임 중복체크
        if (userRepository.existsByNickname(request.getNickname())) {
            throw new DuplicateEmailException("이미 사용중인 닉네임입니다.");
        }
        //비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        //User엔티티 생성
        User user = User.builder()
                .email(request.getEmail())
                .password(encodedPassword)
                .nickname(request.getNickname())
                .build();

        //저장
        User savedUser = userRepository.save(user);

        //응답 DTO로 변환
        return new UserResponse(savedUser);
    }
        public LoginResponse login(LoginRequest request) {
            // 1. 이메일로 사용자 찾기
            User user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new UserNotFoundException("존재하지 않는 이메일입니다"));

            // 2. 비밀번호 확인
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new InvalidCredentialsException("비밀번호가 일치하지 않습니다");
            }

            // 3. JWT 토큰 생성
            String accessToken = jwtUtil.generateAccessToken(user.getId(), user.getEmail());

            // 4. 응답 반환
            return new LoginResponse(
                    accessToken,
                    "Bearer",
                    user.getId(),
                    user.getEmail(),
                    user.getNickname()
            );
        }
}
