package by.academy.web.util;

/**
 * Created with IntelliJ IDEA.
 * Date: 6/22/13
 * Time: 10:21 AM
 */
public enum RequestConstants {
    EVENTS_LIST_ATTRIBUTE("eventList"),
    DATE_INTERVAL("dateInterval"),
    EMAIL_ATTRIBUTE("inputEmail"),
    PASSWORD_ATTRIBUTE("inputPassword"),
    FIRST_NAME_ATTRIBUTE("firstName"),
    SECOND_NAME_ATTRIBUTE("secondName"),
    CITY_ATTRIBUTE("inputAddress"),
    PHONE_ATTRIBUTE("inputPhone"),
    CATEGORY_ID("categoryId"),
    COMMAND_PARAMETER("action");
    private String name;
    private RequestConstants(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
