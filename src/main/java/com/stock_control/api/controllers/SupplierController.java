package com.stock_control.api.controllers;

import com.stock_control.api.dtos.supplier.SupplierCreate;
import com.stock_control.api.dtos.supplier.SupplierResponse;
import com.stock_control.api.dtos.supplier.SupplierUpdate;
import com.stock_control.api.entities.Supplier;
import com.stock_control.api.mappers.ResourceUriHelper;
import com.stock_control.api.services.SupplierService;
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
@RequestMapping("api/v1/suppliers")
public class SupplierController implements ResourceUriHelper {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @PostMapping
    public ResponseEntity<SupplierResponse> create(@RequestBody @Valid SupplierCreate create) {
        SupplierResponse response = supplierService.createSupplier(create);

        URI location = createUriForId(response.id());

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<List<SupplierResponse>> getAllSuppliers(@RequestParam(required = false) String companyName,
                                                                  @RequestParam(required = false, defaultValue = "false") boolean inactivated) {
        List<SupplierResponse> list = supplierService.getAllSuppliers(companyName, inactivated);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{cnpj}")
    public ResponseEntity<SupplierResponse> getSupplier(@PathVariable String cnpj) {
        SupplierResponse response = supplierService.getSupplierByCnpj(cnpj);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{cnpj}/reactivate")
    public ResponseEntity<Void> reactivateSupplier(@PathVariable String cnpj) {
        supplierService.reactivatedSuppliers(cnpj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{cnpj}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable String cnpj) {
        supplierService.deleteSupplierByCnpj(cnpj);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{cnpj}")
    public ResponseEntity<Void> updateSupplier(@PathVariable String cnpj, @RequestBody @Valid SupplierUpdate update) {
        supplierService.updateSupplier(cnpj, update);
        return ResponseEntity.noContent().build();
    }
}
