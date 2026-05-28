package com.stock_control.api.dtos.supplier;

import java.util.UUID;

public record SupplierResponse(

        UUID id,

        String companyName,

        String cnpj,

        String contactEmail,

        boolean active
) {
}
