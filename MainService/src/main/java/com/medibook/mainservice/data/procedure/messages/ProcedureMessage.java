package com.medibook.mainservice.data.procedure.messages;

import com.medibook.mainservice.util.exception.ApplicationException;

public class ProcedureMessage {

    public static final ApplicationException PROCEDURE_NOT_FOUND
            = new ApplicationException("procedure/0001","Procedure not found",404);
    public static final ApplicationException PROCEDURE_NAME_NULL_OR_EMPTY
            = new ApplicationException("procedure/0002","Procedure name cannot be null or empty",400);
    public static final ApplicationException PROCEDURE_DESCRIPTION_NULL_OR_EMPTY
            = new ApplicationException("procedure/0003","Procedure description cannot be null or empty",400);
    public static final ApplicationException PROCEDURE_PRICE_NULL
            = new ApplicationException("procedure/0004","Procedure price cannot be null",400);
    public static final ApplicationException PROCEDURE_LENGTH_NULL
            = new ApplicationException("procedure/0005","Procedure length cannot be null",400);
    public static final ApplicationException PROCEDURE_PRICE_NOT_POSITIVE
            = new ApplicationException("procedure/0006","Procedure price must be higher than 0.00",400);
    public static final ApplicationException DOCTOR_NOT_FOUND = new ApplicationException("procedure/0007","Doctor not found", 404);


}
