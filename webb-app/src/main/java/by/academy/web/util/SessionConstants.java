package by.academy.web.util;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 6/19/13
 * Time: 2:41 PM
 * To change this template use File | Settings | File Templates.
 */
public enum SessionConstants {
    LOCALE_ATTRIBUTE("lang"),
    LOCALE_ID_ATTRIBUTE("langId"),
    PERFORMANCE_ID_ATTRIBUTE("performanceId"),
    PERFORMANCES_ATTRIBUTE("performances"),
    PERFORMANCE_LIST_ATTRIBUTE("performanceList"),
    PERFORMANCE_ANSWER_ATTRIBUTE("editPerformanceList"),
    MENU_ITEM_ATTRIBUTE("menuItem"),
    ANSWER_ATTRIBUTE("answer"),
    MESSAGE_ATTRIBUTE("message"),
    EVENT_ATTRIBUTE("event"),
    EVENT_ID_ATTRIBUTE("eventId"),
    EVENTS_ATTRIBUTE("events"),
    EVENT_ANSWER_ATTRIBUTE("editEventsList"),
    EVENT_DATE("eventsDate"),
    EDIT_EVENT_ANSWER_ATTRIBUTE("editEvent"),
    INPUT_LANG_ID("inputLangId"),
    CATEGORIES_LIST_ATTRIBUTE("categories"),
    INPUT_START_TIME_ATTRIBUTE("inputStartTime"),
    INPUT_END_TIME_ATTRIBUTE("inputEndTime"),
    INPUT_NAME_ATTRIBUTE("inputName"),
    INPUT_SHORTDESCRIPTION_ATTRIBUTE("inputShortDescription"),
    INPUT_DESCRIPTION_ATTRIBUTE("inputDescription"),
    INPUT_IMAGE_ATTRIBUTE("inputImage"),
    INPUT_DATE_INTERVAL("inputDateInteval"),
    INPUT_CATEGORY_ATTRIBUTE("inputCategoryId"),
    TICKETS_PRICE_ATTRIBUTE("ticketsPriceList"),
    INPUT_TICKETS_PRICE_ATTRIBUTE("inputPrice"),
    CITY_ATTRIBUTE("inputCity"),
    PHONE_ATTRIBUTE("inputPhone"),
    ADMIN_ATTRIBUTE("admin"),
    ADMIN_ANSWER_ATTRIBUTE("adminPage"),
    USER_ATTRIBUTE("user"),
    FIRST_NAME_ATTRIBUTE("firstName"),
    SECOND_NAME_ATTRIBUTE("secondName"),
    INPUT_DATE("inputDate"),
    LEGEND_ATTRIBUTE("legend"),
    LEGEND("Добавление события"),
    START_TIME_ATTRIBUTE("startTime"),
    END_TIME_ATTRIBUTE("endTime");
    private String name;

    private SessionConstants(String content) {
        this.name = content;
    }

    public String getName() {
        return name;
    }

}
