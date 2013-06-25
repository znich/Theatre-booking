package by.academy.utils;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * User: Siarhei Poludvaranin
 * Date: 19.05.13
 * Time: 21:23
 * Класс считывает информацию из файла пропертис, который содержит информацию о
 * сообщениях пользователю
 */
public class MessagesProperties {
    private ResourceBundle resourceBundle = null;
    private static MessagesProperties messagesProperties = null;
    public static final String WRONG_EMAIL = "msg.wrongEmail";
    public static final String WRONG_FIRST_NAME = "msg.wrongFirstName";
    public static final String WRONG_SECOND_NAME = "msg.wrongSecondName";
    public static final String WRONG_PASS = "msg.wrongPass";
    public static final String EMAIL_EXIST = "msg.emailExist";
    public static final String REGISTER_SUCCESSFUL = "msg.RegisteredSuccessfully";
    public static final String ERROR_LOGIN = "msg.errorLogin";
    public static final String MAIN_PAGE = "menu.main";
    public static final String EDIT_PERFORMANCE_SUCESS = "msg.editPerformSucces";


    private MessagesProperties() {
        resourceBundle = ResourceBundle.getBundle("messages");
    }

    public static MessagesProperties createPathProperties() {
        if (messagesProperties == null) {
            messagesProperties = new MessagesProperties();
        }
        return messagesProperties;
    }

    public String getProperties(String key, String language) {
        Locale locale = new Locale(language);
        resourceBundle = ResourceBundle.getBundle("messages", locale);
        return resourceBundle.getString(key);
    }
}
