package com.recipe.platform.domain.user.dto;

import com.recipe.platform.domain.user.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponse {
    private final long id;
    private final String email;
    private final String nickname;
    private final LocalDateTime createdAt;

    public UserResponse(User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.createdAt = user.getCreatedAt();
    }
}
