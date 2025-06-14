package com.medibook.mainservice.data.place;

import com.medibook.mainservice.data.doctor.Doctor;
import com.medibook.mainservice.data.doctor.DoctorServiceImpl;
import com.medibook.mainservice.data.place.dto.UpdatePlaceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaceServiceImpl implements IPlaceService {
    private final PlaceRepository placeRepository;
    private final DoctorServiceImpl doctorService;

    @Override
    public Place updatePlace(UpdatePlaceDto dto, String username) {

        Doctor doctor = doctorService.getDoctorByUsername(username);

        Place place = placeRepository.findByDoctor_Id(doctor.getId());
        if (place == null) {
            place = new Place();
            place.setDoctor(doctor);
        }

        place.setAddress(dto.address());
        place.setName(dto.name());
        place.setRoom(dto.room());

        return placeRepository.save(place);
    }

    @Override
    public Place getPlaceFromDoctor(String id) {
        return placeRepository.findByDoctor_Id(id);
    }
}
