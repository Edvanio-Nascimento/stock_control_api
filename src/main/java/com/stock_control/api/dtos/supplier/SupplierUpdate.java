package com.stock_control.api.dtos.supplier;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CNPJ;

public record SupplierUpdate(

        @NotBlank
        @Size(min = 5, max = 100, message = "O campo companyName deve ter entre 5 e 100 caracteres.")
        String companyName,

        @NotBlank
        @Size(min = 5, max = 100, message = "O campo contactEmail deve ter entre 5 e 100 caracteres.")
        @Email
        String contactEmail

) {
}
