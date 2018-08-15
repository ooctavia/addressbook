package com.octavianusosbert.addressbook.Check;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class CheckPhoneNumber {
    Pattern pattern;
    Matcher matcher;

    private static final String phone_formatting = "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$";

    public CheckPhoneNumber() {
        pattern = Pattern.compile(phone_formatting);
    }

    public boolean check(final long phone) {
        matcher = pattern.matcher(Long.toString(phone));
        return matcher.matches();
    }
}
