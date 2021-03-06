package com.dimuthuupeksha.viewer.android.uimodel;

import java.lang.reflect.Method;

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
        } else if (type.equals("java.lang.String")||type.equals("string")
        		||type.equals("int")||type.equals("long")) {
            EditText txt = new EditText(context);
            if (value != null)
                txt.setText(value);

            return txt;
        } else if (type.equals("org.joda.time.LocalDate")||type.equals("date")) {
            DatePicker datePicker = new DatePicker(context);
            if(value!=null){
	            int year = Integer.parseInt(value.split("-")[0]);
	            int month = Integer.parseInt(value.split("-")[1])-1;
	            int date = Integer.parseInt(value.split("-")[2]);
	            datePicker.init(year, month, date, null);
            }
            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            if (currentapiVersion >= 11) {
              try {
                Method m = datePicker.getClass().getMethod("setCalendarViewShown", boolean.class);
                m.invoke(datePicker, false);
              }
              catch (Exception e) {} // eat exception in our case
            }
        	return datePicker;
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
