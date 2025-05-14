package com.medibook.mainservice.util.generic;

public interface Validator <O extends Record>{
    void validate(O dto);
}
