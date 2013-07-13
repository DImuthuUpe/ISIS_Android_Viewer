package com.dimuthuupeksha.viewer.android.uimodel;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

public class ViewMapper {
    public static View convertToView(String type,Context context){

        if(type.equals("java.lang.Long")){
            return new EditText(context);
        }else if(type.equals("boolean")){
            return new CheckBox(context);
        }else if(type.equals("java.lang.Long")){
            return new EditText(context);
        }else if(type.equals("java.lang.String")){
            return new EditText(context);
        }else if(type.equals("org.joda.time.LocalDate")){
            return new DatePicker(context );
        }else if(type.equals("java.math.BigDecimal")){
            return new EditText(context );
        }

        return null;
    }
}
