package by.academy.web.validator;

import by.academy.utils.MessagesProperties;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 */
public class UserValidator {

    private String locale;

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public List<String> validateRegisterParams(String firstName, String secondName, String email, String password) {
        List<String> errors = new ArrayList<String>();

        if(!checkPassword(password)){
            errors.add(MessagesProperties.createPathProperties().getProperties(MessagesProperties.WRONG_PASS, locale));
        }

        if(!checkEmail(email)){
            errors.add(MessagesProperties.createPathProperties().getProperties(MessagesProperties.WRONG_EMAIL, locale));
        }

        if (!checkFirstName(firstName)){
            errors.add(MessagesProperties.createPathProperties().getProperties(MessagesProperties.WRONG_FIRST_NAME, locale));
        }

        if (!checkLastName(secondName)){
            errors.add(MessagesProperties.createPathProperties().getProperties(MessagesProperties.WRONG_SECOND_NAME, locale));
        }


        return errors;
    }

    public boolean checkPassword(String password) {
        boolean flag;

        Pattern pattern = Pattern.compile("[a-zA-Z0-9]{3,10}");
        Matcher matcher = pattern.matcher(password);
        flag = matcher.matches();
        return flag;
    }

    public boolean checkEmail(String email) {
        boolean flag;

        Pattern pattern = Pattern.compile("^[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})$");
        Matcher matcher = pattern.matcher(email);
        flag = matcher.matches();
        return flag;
    }

    public boolean checkFirstName(String firstName) {
        boolean flag;

        Pattern pattern = Pattern.compile("[a-zа-яA-ZА-Я ]{3,20}");
        Matcher matcher = pattern.matcher(firstName);
        flag = matcher.matches();
        return flag;
    }

    public boolean checkLastName(String lastName) {
        boolean flag;

        Pattern pattern = Pattern.compile("[a-zа-яA-ZА-Я ]{3,20}");
        Matcher matcher = pattern.matcher(lastName);
        flag = matcher.matches();
        return flag;
    }
}
