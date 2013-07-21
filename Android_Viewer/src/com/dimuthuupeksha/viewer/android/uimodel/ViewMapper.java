package com.dimuthuupeksha.viewer.android.uimodel;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

public class ViewMapper {
    public static View convertToView(String type, Context context, String value) {
        //System.out.println("2");
        
        if (type.equals("java.lang.Long")) {
            EditText txt = new EditText(context);
            if (value != null)
                txt.setText(value);
            return txt;
        } else if (type.equals("boolean")) {
            CheckBox box = new CheckBox(context);
            if(value!=null)
                box.setChecked(Boolean.parseBoolean(value));
            return box;
        } else if (type.equals("java.lang.Long")) {
            EditText txt = new EditText(context);
            if (value != null)
                txt.setText(value);
            return txt;
        } else if (type.equals("java.lang.String")) {
            EditText txt = new EditText(context);
            if (value != null)
                txt.setText(value);

            return txt;
        } else if (type.equals("org.joda.time.LocalDate")) {
            return new DatePicker(context);
        } else if (type.equals("java.math.BigDecimal")) {
            EditText txt = new EditText(context);
            if (value != null)
                txt.setText(value);

            return txt;
        }
        
        if(value!=null){  // Temporary... Need to be removed. Find a better approach
            EditText txt = new EditText(context);
            txt.setText(value);
            return txt;
        }
        //System.out.println("3");
        
        return null;
    }
}
