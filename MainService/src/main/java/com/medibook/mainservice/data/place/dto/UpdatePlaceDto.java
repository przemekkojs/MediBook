package com.medibook.mainservice.data.place.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdatePlaceDto(@NotBlank String name,@NotBlank String room, @NotBlank String address) {
}
