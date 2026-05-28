package com.stock_control.api.services;

import com.stock_control.api.dtos.supplier.SupplierCreate;
import com.stock_control.api.dtos.supplier.SupplierResponse;
import com.stock_control.api.dtos.supplier.SupplierUpdate;
import com.stock_control.api.entities.Supplier;
import com.stock_control.api.exceptions.BusinessException;
import com.stock_control.api.exceptions.ResourceNotFoundException;
import com.stock_control.api.mappers.supplier.SupplierMapper;
import com.stock_control.api.repositories.SupplierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    public SupplierService(SupplierRepository supplierRepository, SupplierMapper supplierMapper) {
        this.supplierRepository = supplierRepository;
        this.supplierMapper = supplierMapper;
    }

    @Transactional
    public SupplierResponse createSupplier(SupplierCreate create) {
        if (supplierRepository.existsByCnpj(create.cnpj())) {
            throw new BusinessException("Já tem um CNPJ cadastrado com este CNPJ: " + create.cnpj());
        }

        Supplier supplier = supplierMapper.toSupplier(create);
        supplierRepository.save(supplier);
        return supplierMapper.toSupplierResponse(supplier);
    }

    @Transactional(readOnly = true)
    public List<SupplierResponse> getAllSuppliers(String name, boolean inactivated) {

        if (name != null) {
            return supplierRepository.findByCompanyNameContainingIgnoreCase(name)
                    .stream()
                    .map(supplierMapper::toSupplierResponse)
                    .toList();
        }

        if (inactivated) {
            return supplierRepository.findAllInactivated()
                    .stream()
                    .map(supplierMapper::toSupplierResponse)
                    .toList();
        }

        return supplierRepository.findAll()
                .stream()
                .map(supplierMapper::toSupplierResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public SupplierResponse getSupplierByCnpj(String cnpj) {
        Supplier supplier = supplierRepository.findByCnpj(cnpj)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum fornecedor encontrado com esse CNPJ: " + cnpj));

        return supplierMapper.toSupplierResponse(supplier);
    }

    @Transactional
    public void reactivatedSuppliers(String cnpj) {
        Supplier supplier = supplierRepository.findByCnpjInactivated(cnpj)
                .orElseThrow(() -> new ResourceNotFoundException("Fornecedor encontrado com esse CNPJ: " + cnpj));

        if (supplier.isActive()) {
            throw new BusinessException("Este fornecedor já está ativo.");
        }

        supplier.setActive(true);
        supplierRepository.save(supplier);
    }

    @Transactional
    public void deleteSupplierByCnpj(String cnpj) {
        Supplier supplier = supplierRepository.findByCnpjInactivated(cnpj)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum fornecedor encontrado com esse CNPJ: " + cnpj));

        supplier.setActive(false);
    }

    @Transactional
    public void updateSupplier(String cnpj, SupplierUpdate update) {
        Supplier supplier = supplierRepository.findByCnpj(cnpj)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum fornecedor encontrado com esse CNPJ: " + cnpj));

        if (supplier.getCnpj() != null) {
            throw new BusinessException("Não é possível atualizar. Já existe outro fornecedor  com esse cnpj: " + supplier.getCnpj());
        }

        supplierMapper.updateSupplier(update, supplier);
        supplierMapper.toSupplierResponse(supplier);
    }

}
