package com.stock_control.api.repositories;

import com.stock_control.api.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    // Mudado para findByIdInactivated (passado: inativada)
    @Query(value = "SELECT * FROM tb_categories WHERE id = :id", nativeQuery = true)
    Optional<Category> findByIdInactivated(@Param("id") UUID id);

    // Mudado para findAllInactivated (plural: todas as inativadas)
    @Query(value = "SELECT * FROM tb_categories WHERE active = false", nativeQuery = true)
    List<Category> findAllInactivated();

}
