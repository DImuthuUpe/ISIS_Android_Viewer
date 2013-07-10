package com.dimuthuupeksha.viewer.android.applib.representation;

import java.io.Serializable;

public class JsonRepr implements Serializable{
    
    
    public static <T> T FromString(String str){
        return null;
    }
    private static <T> T AsT(Object list){
        return (T) list;
    }

    public String AsJson()
    {
        return "";
    }

}
