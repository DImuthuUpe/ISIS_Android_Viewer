package com.dimuthuupeksha.viewer.android.applib.exceptions;

import com.dimuthuupeksha.viewer.android.applib.representation.ErrorRepr;

public class JsonParseException extends Exception{

    private ErrorRepr error;
    public JsonParseException(ErrorRepr error) {
        this.error=error;
    }
    
    @Override
    public String toString() {
        return error.getMessage();
    }
    
    @Override
    public void printStackTrace() {
        System.out.println(error.getMessage());
    }
}
