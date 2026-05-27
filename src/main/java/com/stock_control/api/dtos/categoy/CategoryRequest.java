package com.stock_control.api.dtos.categoy;

import java.util.UUID;

public record CategoryRequest(

        UUID id,

        String name,

        String description,

        boolean active
) {
}
