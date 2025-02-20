package com.FoodOrder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.FoodOrder.model.Category;
import com.FoodOrder.model.User;
import com.FoodOrder.service.CategoryService;
import com.FoodOrder.service.UserService;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/admin/category")
    public ResponseEntity<Category> createCategory(
            @RequestHeader("Authorization") String jwt, @RequestBody Category category) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Category createCategory = categoryService.createCategory(category.getName(), user.getId());

        return new ResponseEntity<>(createCategory, HttpStatus.CREATED);
    }

    @GetMapping("/admin/category/restaurant")
    public ResponseEntity<List<Category>> getCategoryRestaurant(@RequestHeader("Authorization") String jwt)
            throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        List<Category> categories = categoryService.findCategoryByRestaurantId(user.getId());

        return new ResponseEntity<>(categories, HttpStatus.CREATED);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Category> findCategoryById(
            @RequestHeader("Authorization") String jwt, @PathVariable Long categoryId) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Category category = categoryService.findCategoryById(categoryId);

        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }
}
