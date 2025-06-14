package com.medibook.mainservice.data.place;

import com.medibook.mainservice.data.place.dto.UpdatePlaceDto;

public interface IPlaceService {
    Place updatePlace(UpdatePlaceDto dto, String username);
    Place getPlaceFromDoctor(String id);
}
