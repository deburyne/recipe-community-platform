package com.recipe.platform.domain.user;

import com.recipe.platform.domain.user.dto.LoginRequest;
import com.recipe.platform.domain.user.dto.LoginResponse;
import com.recipe.platform.domain.user.dto.SignupRequest;
import com.recipe.platform.domain.user.dto.UserResponse;
import com.recipe.platform.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor

public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;

    //바탕화면 회원가입 API
    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@RequestBody SignupRequest request) {
        UserResponse response = userService.signup(request);
        return ResponseEntity.ok(response);
    }

    // 로그인 API
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = userService.login(request);
        return ResponseEntity.ok(response);
    }

    // ------테스트용API--------
    @PostMapping("/test")
    public User createTestUser() {
        User user = User.builder()
                .email("test@example.com")
                .password("password123")
                .nickname("테스터")
                .build();

        return userRepository.save(user);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/email/{email}")
    public User getUserByEmail(@PathVariable String email) {
        return userRepository.findByEmail(email)
                .orElse(null);
    }
}
