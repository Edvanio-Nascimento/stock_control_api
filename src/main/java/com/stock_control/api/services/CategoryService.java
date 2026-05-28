package com.stock_control.api.services;

import com.stock_control.api.dtos.categoy.CategoryCreate;
import com.stock_control.api.dtos.categoy.CategoryResponse;
import com.stock_control.api.dtos.categoy.CategoryUpdate;
import com.stock_control.api.entities.Category;
import com.stock_control.api.exceptions.BusinessException;
import com.stock_control.api.exceptions.ResourceNotFoundException;
import com.stock_control.api.mappers.category.CategoryMapper;
import com.stock_control.api.repositories.CategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Transactional
    public CategoryResponse createCategory(CategoryCreate create) {
        if (categoryRepository.existsByNameIgnoreCase(create.name())){
            throw new BusinessException("Já existe uma categoria cadastrada com esse nome: " + create.name());
        }

        Category category = categoryMapper.toCategory(create);

        category = categoryRepository.save(category);

        return categoryMapper.toCategoryResponse(category);
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toCategoryResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com esse id: " + id));

        return categoryMapper.toCategoryResponse(category);
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getFilteredCategories(String name, boolean inactived) {

        if (name != null && !name.isEmpty()) {
            return categoryRepository.findByNameContainingIgnoreCase(name)
                    .stream()
                    .map(categoryMapper::toCategoryResponse)
                    .toList();
        }

        if (inactived) {
            return categoryRepository.findAllInactivated()
                    .stream()
                    .map(categoryMapper::toCategoryResponse)
                    .toList();
        }

        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toCategoryResponse)
                .toList();
    }

    @Transactional
    public CategoryResponse updateCategory(UUID id, CategoryUpdate update) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com esse id: " + id));

        if (!category.getName().equalsIgnoreCase(update.name()) &&
                categoryRepository.existsByNameIgnoreCase(update.name())) {
            throw new BusinessException("Não é possível atualizar. Já existe outra categoria com o nome: " + update.name());
        }

        categoryMapper.updateCategory(update, category);

        return categoryMapper.toCategoryResponse(category);
    }

    @Transactional
    public void deleteCategory(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com esse id: " + id));

        category.setActive(false);
    }

    @Transactional
    public void reactivateCategory(UUID id) {
        Category category = categoryRepository.findByIdInactivated(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com esse id: " + id));

        if (category.isActive()) {
            throw new BusinessException("Esta categoria já está ativa."); // Evita queries desnecessárias
        }

        category.setActive(true);
        categoryRepository.save(category);
    }
}
