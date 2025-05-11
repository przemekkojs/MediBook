package com.medibook.mainservice.data.workhours.messages;

import com.medibook.mainservice.util.exception.ApplicationException;

public class WorkhourMessage {

    public static final ApplicationException DAY_BELOW_1 =new ApplicationException("workhours/0001","Day cannot be lower than 1", 400);
    public static final ApplicationException DAY_ABOWE_7 =  new ApplicationException("workhours/0002","Day cannot be higher than 7", 400);
    public static final ApplicationException START_TIME_NULL = new ApplicationException("workhours/0003","Start time cannot be null", 400);
    public static final ApplicationException END_TIME_NULL = new ApplicationException("workhours/0004","End time cannot be null", 400);
    public static final ApplicationException START_TIME_AFTER_END_TIME_NULL = new ApplicationException("workhours/0005","Start time cannot be after end time", 400);
    public static final ApplicationException DOCTOR_NOT_FOUND = new ApplicationException("workhours/0006","Doctor not found", 404);
    public static final ApplicationException WORKHOURS_NOT_FOUND = new ApplicationException("workhours/0007","Work hours not found", 404);

}
