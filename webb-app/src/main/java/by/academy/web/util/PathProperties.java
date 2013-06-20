package by.academy.web.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 24.05.13
 * Time: 13:15
 * Класс считывает информацию из файла пропертис, который содержит информацию о
 * путях к страницам
 */
public class PathProperties {
    private Properties properties = null;
    private static PathProperties pathProperties = null;
    public static final String LOGIN_PAGE = "LOGIN_PAGE";
    public static final String REGISTRATION_PAGE = "REGISTRATION_PAGE";
    public static final String PROFILE_ADMIN_PAGE = "PROFILE_ADMIN_PAGE";
    public static final String PROFILE_USER_PAGE = "PROFILE_USER_PAGE";
    public static final String PERFORMANCE_LIST_PAGE = "PERFORMANCE_LIST_PAGE";
    public static final String EVENTS_LIST_PAGE="EVENTS_LIST_PAGE";
    public static final String PERFORMANCE_PAGE = "PERFORMANCE_PAGE";
    public static final String ADMIN_PAGE = "ADMIN_PAGE";

    private PathProperties() throws IOException {

        properties = new Properties();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("urls.xml");
        properties.loadFromXML(inputStream);
        inputStream.close();
    }

    public static PathProperties createPathProperties() throws IOException {
        if (pathProperties == null) {
            pathProperties = new PathProperties();
        }
        return pathProperties;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
