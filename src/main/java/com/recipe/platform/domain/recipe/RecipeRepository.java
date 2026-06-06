package com.recipe.platform.domain.recipe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByUserId(Long userId);

    // 재료 기반 검색 (여러 재료 중 하나라도 포함되면 추천)
    @Query("SELECT DISTINCT r FROM Recipe r WHERE " +
            "r.ingredients LIKE CONCAT('%', :ingredient1, '%') AND " +
            "(:ingredient2 IS NULL OR r.ingredients LIKE CONCAT('%', :ingredient2, '%')) AND " +
            "(:ingredient3 IS NULL OR r.ingredients LIKE CONCAT('%', :ingredient3, '%'))")
    List<Recipe> findByIngredients(
            @Param("ingredient1") String ingredient1,
            @Param("ingredient2") String ingredient2,
            @Param("ingredient3") String ingredient3
    );
}
