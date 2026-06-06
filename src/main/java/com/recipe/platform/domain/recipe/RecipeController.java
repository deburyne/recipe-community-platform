package com.recipe.platform.domain.recipe;

import com.recipe.platform.domain.recipe.dto.RecipeRequest;
import com.recipe.platform.domain.recipe.dto.RecipeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {
    private final RecipeService recipeService;

    // 레시피 등록
    @PostMapping
    public ResponseEntity<RecipeResponse> createRecipe(
            Authentication authentication,
            @RequestBody RecipeRequest request) {
        Long userId = (Long) authentication.getPrincipal();
        return ResponseEntity.ok(recipeService.createRecipe(userId, request));
    }

    // 전체 레시피 조회
    @GetMapping
    public ResponseEntity<List<RecipeResponse>> getAllRecipes() {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }

    // 특정 레시피 조회
    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponse> getRecipe(@PathVariable Long id) {
        return ResponseEntity.ok(recipeService.getRecipe(id));
    }

    // 레시피 수정
    @PutMapping("/{id}")
    public ResponseEntity<RecipeResponse> updateRecipe(
            Authentication authentication,
            @PathVariable Long id,
            @RequestBody RecipeRequest request) {
        Long userId = (Long) authentication.getPrincipal();
        return ResponseEntity.ok(recipeService.updateRecipe(userId, id, request));
    }

    // 레시피 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(
            Authentication authentication,
            @PathVariable Long id) {
        Long userId = (Long) authentication.getPrincipal();
        recipeService.deleteRecipe(userId, id);
        return ResponseEntity.noContent().build();
    }

    // 재료 기반 레시피 추천
    @GetMapping("/recommend")
    public ResponseEntity<List<RecipeResponse>> recommendByIngredients(
            @RequestParam List<String> ingredients) {
        return ResponseEntity.ok(recipeService.recommendByIngredients(ingredients));
    }
}
