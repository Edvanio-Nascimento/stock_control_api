package com.stock_control.api.mappers.category;

import com.stock_control.api.dtos.categoy.CategoryCreate;
import com.stock_control.api.dtos.categoy.CategoryRequest;
import com.stock_control.api.dtos.categoy.CategoryUpdate;
import com.stock_control.api.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    Category toCategory(CategoryCreate create);

    CategoryRequest toCategoryRequest(Category category);

    void updateCategory(CategoryUpdate update, @MappingTarget Category category);

}
