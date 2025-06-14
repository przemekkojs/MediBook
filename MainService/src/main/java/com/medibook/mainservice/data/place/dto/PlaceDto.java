package com.medibook.mainservice.data.place.dto;

import jakarta.validation.constraints.NotBlank;

public record PlaceDto(@NotBlank long id,@NotBlank String name,@NotBlank  String room,@NotBlank  String address,@NotBlank  String doctorID) {
}
