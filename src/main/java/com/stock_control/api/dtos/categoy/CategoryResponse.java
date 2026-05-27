package com.stock_control.api.dtos.categoy;

import java.util.UUID;

public record CategoryResponse(

        UUID id,

        String name,

        String description,

        boolean active
) {
}
