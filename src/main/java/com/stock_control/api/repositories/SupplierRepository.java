package com.stock_control.api.repositories;

import com.stock_control.api.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SupplierRepository extends JpaRepository<Supplier, UUID> {

    boolean existsByCnpj(String cnpj);

    Optional<Supplier> findByCnpj(String cnpj);

    List<Supplier> findByCompanyNameContainingIgnoreCase(String companyName);

    @Query(value = "SELECT * FROM tb_suppliers WHERE cnpj = :cnpj", nativeQuery = true)
    Optional<Supplier> findByCnpjInactivated(@Param("cnpj") String cnpj);

    @Query(value = "SELECT * FROM tb_suppliers WHERE active = false", nativeQuery = true)
    List<Supplier> findAllInactivated();

}
