package com.recipe.platform.domain.recipe;

import com.recipe.platform.domain.recipe.dto.RecipeRequest;
import com.recipe.platform.domain.recipe.dto.RecipeResponse;
import com.recipe.platform.domain.user.User;
import com.recipe.platform.domain.user.UserRepository;
import com.recipe.platform.global.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    // 레시피 등록
    public RecipeResponse createRecipe(Long userId, RecipeRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("유저를 찾을 수 없습니다"));

        Recipe recipe = Recipe.builder()
                .user(user)
                .title(request.getTitle())
                .description(request.getDescription())
                .ingredients(request.getIngredients())
                .instructions(request.getInstructions())
                .build();

        return new RecipeResponse(recipeRepository.save(recipe));
    }

    // 전체 레시피 조회
    public List<RecipeResponse> getAllRecipes() {
        return recipeRepository.findAll()
                .stream()
                .map(RecipeResponse::new)
                .collect(Collectors.toList());
    }

    // 특정 레시피 조회
    public RecipeResponse getRecipe(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("레시피를 찾을 수 없습니다"));
        return new RecipeResponse(recipe);
    }

    // 레시피 수정
    public RecipeResponse updateRecipe(Long userId, Long recipeId, RecipeRequest request) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("레시피를 찾을 수 없습니다"));

        if (!recipe.getUser().getId().equals(userId)) {
            throw new RuntimeException("본인의 레시피만 수정할 수 있습니다");
        }

        Recipe updated = Recipe.builder()
                .id(recipe.getId())
                .user(recipe.getUser())
                .title(request.getTitle())
                .description(request.getDescription())
                .ingredients(request.getIngredients())
                .instructions(request.getInstructions())
                .createdAt(recipe.getCreatedAt())
                .build();

        return new RecipeResponse(recipeRepository.save(updated));
    }

    // 레시피 삭제
    public void deleteRecipe(Long userId, Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("레시피를 찾을 수 없습니다"));

        if (!recipe.getUser().getId().equals(userId)) {
            throw new RuntimeException("본인의 레시피만 삭제할 수 있습니다");
        }

        recipeRepository.delete(recipe);
    }
}
