package com.madikhan.estore.validator;

public class AuthenticationValidator {

    private static final String MAIL_REGEX = "^[\\\\w!#$%&’*+/=?`{|}~^-]+(?:\\\\.[\\\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\\\.)+[a-zA-Z]{2,6}$";
    private static final String PASSWORD_REGEX = "[a-zA-Z0-9~!@#$%^&*]{6,}";
    private static final String PHONE_REGEX = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";

    public static boolean isPasswordCorrect(String password, String passwordInDataBase) {
        return passwordInDataBase.equals(password);
    }

    public static boolean isEmailValid(String email) {
        return email.matches(MAIL_REGEX);
    }

    public static boolean isPasswordValid(String password) {
        return password.matches(PASSWORD_REGEX);
    }

    public static boolean isPhoneNumberValid(String phoneNumber) {
        return phoneNumber.matches(PHONE_REGEX);
    }

}
