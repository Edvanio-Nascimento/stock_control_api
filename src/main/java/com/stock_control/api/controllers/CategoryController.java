package com.stock_control.api.controllers;

import com.stock_control.api.dtos.categoy.CategoryCreate;
import com.stock_control.api.dtos.categoy.CategoryRequest;
import com.stock_control.api.dtos.categoy.CategoryUpdate;
import com.stock_control.api.mappers.ResourceUriHelper;
import com.stock_control.api.services.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/categories")
public class CategoryController implements ResourceUriHelper {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<CategoryRequest> create(@RequestBody @Valid CategoryCreate create) {
        CategoryRequest request = categoryService.createCategory(create);

        URI location = createUriForId(request.id());

        return ResponseEntity.created(location).body(request);
    }

    @GetMapping
    public ResponseEntity<List<CategoryRequest>> getAllCategories(@RequestParam(required = false, defaultValue = "false") boolean inactivated) {

        if(inactivated) {
            List<CategoryRequest> list = categoryService.getAllInactivatedCategories();
            return ResponseEntity.ok(list);
        }

        List<CategoryRequest> list = categoryService.getAllCategories();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryRequest> getCategory(@PathVariable UUID id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryRequest> updateCategory(@PathVariable UUID id,
                                                          @RequestBody @Valid CategoryUpdate update) {
        return ResponseEntity.ok(categoryService.updateCategory(id, update));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/reactivate")
    public ResponseEntity<Void> reactivateCategory(@PathVariable UUID id) {
        categoryService.reactivateCategory(id);
        return ResponseEntity.noContent().build();
    }
}
