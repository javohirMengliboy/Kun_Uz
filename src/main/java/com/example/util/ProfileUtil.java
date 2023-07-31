package com.example.util;

import com.example.dto.ProfileDTO;
import com.example.exp.AppBadRequestException;

public class ProfileUtil {
    public static void check(ProfileDTO dto) {
        checkingName(dto.getName());
        checkingSurname(dto.getSurname());
        if (!checkingPhone(dto.getPhone())){
            throw new AppBadRequestException("Number is invalid");
        }
        checkingPassword(dto.getPassword());
        checkingEmail(dto.getEmail());
    }

    public static void checkingEmail(String email) {
        if (!email.contains("@") || !email.contains("mail")){
            throw new AppBadRequestException("Email is invalid");
        }
    }

    public static void checkingPassword(String password) {
        if (password.isBlank() && password.length() < 4) {
            throw new AppBadRequestException("There are not enough characters");
        }
        char[] arr = password.toCharArray();
        int upper = 0;
        int lower = 0;
        int number = 0;
        for (char c : arr) {
            if (c == 32){
                throw new AppBadRequestException("There can be no spaces");
            }else if (c > 64 && c < 91) {
                upper++;
            }else if (c > 96 && c < 123) {
                lower++;
            }else if (c > 47 && c < 58) {
                number++;
            }
        }
        if (upper == 0 || lower == 0 || number == 0){
            throw new AppBadRequestException("Password must contain at least one uppercase letter, one lowercase letter, one number and one symbol");
        }
    }

    public static void checkingName(String value){
        if (value.length() < 3 || value.isBlank()){
            throw new AppBadRequestException("There are not enough characters");
        }
        isCapital(value);
        char[] arr = value.toCharArray();
        for (char c : arr) {
            if (c > 32 && c < 58) {
                throw new AppBadRequestException("There is an excess character");
            }
        }
    }

    public static void checkingSurname(String value){
        if (value.length() < 2 || value.isBlank()){
            throw new AppBadRequestException("There are not enough characters");
        }
        isCapital(value);
        char[] arr = value.toCharArray();
        for (char c : arr) {
            if (c > 32 && c < 58) {
                throw new AppBadRequestException("There is an excess character");
            }
        }
    }

    public static boolean checkingPhone(String phone){
        boolean bool = true;
        char[] arr = phone.toCharArray();

        if (phone.length() != 13 || !phone.startsWith("+998")){
            bool = false;
        }

        for (int i = 1; i< arr.length; i++) {
            if (arr[i] < 48 || arr[i] > 57) {
                bool = false;
                break;
            }
        }
        return bool;
    }

    public static void isCapital(String value){
        if (value.charAt(0) < 64 || value.charAt(0) > 91) {
            throw new AppBadRequestException("Note the capital letter");
        }
    }

}
