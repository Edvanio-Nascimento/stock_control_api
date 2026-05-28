package com.stock_control.api.dtos.supplier;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CNPJ;

public record SupplierCreate(

        @NotBlank
        @Size(min = 5, max = 100, message = "O campo companyName deve ter entre 5 e 100 caracteres.")
        String companyName,

        @NotBlank
        @Size(max = 14, message = "O campo cnpj deve ter exatamente 14 caracteres.")
        @CNPJ
        String cnpj,

        @NotBlank
        @Size(min = 5, max = 100, message = "O campo contactEmail deve ter entre 5 e 100 caracteres.")
        @Email
        String contactEmail
) {
}
