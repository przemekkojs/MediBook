package com.medibook.mainservice.data.workhours.validation;

import com.medibook.mainservice.util.generic.Validator;
import com.medibook.mainservice.data.workhours.dto.CreateWorkhoursDto;
import com.medibook.mainservice.data.workhours.messages.WorkhourMessage;
import org.springframework.stereotype.Component;

@Component
public class CreateWorkhoursValidator implements Validator<CreateWorkhoursDto> {

    public void validate(CreateWorkhoursDto dto){
        if(dto.day() <1){
           throw WorkhourMessage.DAY_BELOW_1;
        }

        if(dto.day() >7){
            throw WorkhourMessage.DAY_ABOWE_7;
        }

        if(dto.startTime() == null){
            throw WorkhourMessage.START_TIME_NULL;
        }

        if(dto.endTime() == null){
            throw WorkhourMessage.END_TIME_NULL;
        }

        if(dto.startTime().after(dto.endTime())){
            throw WorkhourMessage.START_TIME_AFTER_END_TIME_NULL;
        }


    }
}
