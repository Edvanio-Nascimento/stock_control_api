package com.stock_control.api.mappers.supplier;

import com.stock_control.api.dtos.supplier.SupplierCreate;
import com.stock_control.api.dtos.supplier.SupplierResponse;
import com.stock_control.api.dtos.supplier.SupplierUpdate;
import com.stock_control.api.entities.Supplier;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SupplierMapper {

    SupplierMapper INSTANCE = Mappers.getMapper(SupplierMapper.class);

    Supplier toSupplier(SupplierCreate create);

    SupplierResponse toSupplierResponse(Supplier supplier);

    void updateSupplier(SupplierUpdate update, @MappingTarget Supplier supplier);
}
