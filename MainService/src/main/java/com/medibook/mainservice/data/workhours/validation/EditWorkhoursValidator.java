package com.medibook.mainservice.data.workhours.validation;

import com.medibook.mainservice.util.generic.Validator;
import com.medibook.mainservice.data.workhours.dto.EditWorkhoursDto;
import com.medibook.mainservice.data.workhours.messages.WorkhourMessage;
import org.springframework.stereotype.Component;

@Component
public class EditWorkhoursValidator implements Validator<EditWorkhoursDto> {
    public void validate(EditWorkhoursDto dto){
        if(dto.startTime() == null){
            throw WorkhourMessage.START_TIME_NULL;
        }

        if(dto.endTime() == null){
            throw WorkhourMessage.END_TIME_NULL;
        }

        if(dto.startTime().isAfter(dto.endTime())){
            throw WorkhourMessage.START_TIME_AFTER_END_TIME_NULL;
        }
    }
}
