package com.example.gwt.gwtApp.shared;

public class FieldValidator {

    public static boolean isValidData(String data) {
        if (data == null) {
            return false;
        }
        return data.length() >= 3;
    }
}