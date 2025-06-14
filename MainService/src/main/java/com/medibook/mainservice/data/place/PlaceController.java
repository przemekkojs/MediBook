package com.medibook.mainservice.data.place;

import com.medibook.mainservice.data.place.dto.PlaceDto;
import com.medibook.mainservice.data.place.dto.UpdatePlaceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/places")
@RequiredArgsConstructor
public class PlaceController {

    private final IPlaceService placeService;

    @PutMapping("/doctor")
    public ResponseEntity<PlaceDto> updatePlace(@RequestBody UpdatePlaceDto dto, JwtAuthenticationToken auth) {

        String username = auth.getToken().getClaimAsString("preferred_username");
        Place updatedPlace = placeService.updatePlace(dto, username);


        return ResponseEntity.ok(new PlaceDto(
                updatedPlace.getId(),
                updatedPlace.getName(),
                updatedPlace.getRoom(),
                updatedPlace.getAddress(),
                updatedPlace.getDoctor().getId()
        ));

    }

    @GetMapping("/{doctor_id}")
    public ResponseEntity<PlaceDto> getPlaceById(@PathVariable String id) {
        Place place = placeService.getPlaceFromDoctor(id);

        if (place == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new PlaceDto(
                place.getId(),
                place.getName(),
                place.getRoom(),
                place.getAddress(),
                place.getDoctor().getId()
        ));
    }


}
