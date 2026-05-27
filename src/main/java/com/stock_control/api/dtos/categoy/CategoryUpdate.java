package com.stock_control.api.dtos.categoy;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryUpdate(

        @NotBlank(message = "O campo é obrigátorio.")
        @Size(min = 5, max = 100, message = "O campo name deve conter entre 5 e 100 caracteres.")
        String name,

        @NotBlank(message = "O campo é obrigátorio.")
        @Size(min = 5, max = 500, message = "O campo description deve conter entre 5 e 500 caracteres.")
        String description
) {
}
