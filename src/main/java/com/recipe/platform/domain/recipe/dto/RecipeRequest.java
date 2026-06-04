package com.recipe.platform.domain.recipe.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RecipeRequest {
    private String title;
    private String description;
    private String ingredients;
    private String instructions;
}
