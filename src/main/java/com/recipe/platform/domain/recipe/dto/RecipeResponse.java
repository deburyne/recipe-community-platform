package com.recipe.platform.domain.recipe.dto;

import com.recipe.platform.domain.recipe.Recipe;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter

public class RecipeResponse {
    private Long id;
    private Long userId;
    private String title;
    private String description;
    private String ingredients;
    private String instructions;
    private LocalDateTime createdAt;

    public RecipeResponse(Recipe recipe) {
        this.id = recipe.getId();
        this.userId = recipe.getUser().getId();
        this.title = recipe.getTitle();
        this.description = recipe.getDescription();
        this.ingredients = recipe.getIngredients();
        this.instructions = recipe.getInstructions();
        this.createdAt = recipe.getCreatedAt();
    }
}
