package by.academy.web.commands;

import by.academy.domain.User;
import by.academy.domain.UserRole;
import by.academy.web.util.RequestConstants;
import by.academy.web.util.SessionConstants;
import by.academy.web.wrapper.IWrapper;

/**
 * Фабрика, которая по запросу создаёт необходимую команду.
 */
public enum CommandFactory {
    LOGIN,
    REGISTRATOR,
    LOGOUT,
    SHOW_LOGIN_FORM,
    SHOW_REG_FORM,
    SHOW_PERF_LIST,
    SHOW_EVENT_LIST,
    USER_PROFILE,
    ADMIN_SHOW_PERFORMANCES,
    ADMIN_SHOW_EVENTS,
    SHOW_EDIT_PERFORMANCE,
    EDIT_PERFORMANCE,
    SHOW_ADD_PERFORMANCE,
    SHOW_EDIT_EVENT,
    EDIT_EVENT,
    SHOW_ADD_EVENT,
    DELETE_PERFORMANCE,
    DELETE_EVENT,
    ADMIN_SHOW_MAIN;

    public static ICommand createCommand(IWrapper wrapper) {
        String commandStr = wrapper.getRequest().getParameter(RequestConstants.COMMAND_PARAMETER.getName());
        CommandFactory commandEnum;
        User user;
        try {
            user = (User) wrapper.getSession().getAttribute(SessionConstants.USER_ATTRIBUTE.getName());

            commandEnum = CommandFactory.valueOf(commandStr.toUpperCase());
            if (user != null) {
                UserRole role = user.getRole();
                switch (role) {
                    case ADMIN:
                        switch (commandEnum) {
                            case ADMIN_SHOW_PERFORMANCES:
                                return new AdminShowPerformancesCommand(wrapper);
                            case DELETE_PERFORMANCE:
                                return new DeletePerformanceCommand(wrapper);
                            case DELETE_EVENT:
                            	return new DeleteEventCommand(wrapper);                            		
                            case SHOW_EDIT_PERFORMANCE:
                                return new ShowEditPerformanceCommand(wrapper);
                            case EDIT_PERFORMANCE:
                                return new EditPerformanceCommand(wrapper);
                            case SHOW_ADD_PERFORMANCE:
                                return new ShowAddPerformanceCommand(wrapper);
                            case ADMIN_SHOW_EVENTS:
                                return new AdminShowEventsCommand(wrapper);
                            case SHOW_EDIT_EVENT:
                                return new ShowEditEventCommand(wrapper);
                            case EDIT_EVENT:
                                return new EditEventCommand(wrapper);
                            case SHOW_ADD_EVENT:
                                return new ShowAddEventCommand(wrapper);
                            case ADMIN_SHOW_MAIN:
                                return new ShowAdminPageCommand(wrapper);
                            case SHOW_EVENT_LIST:
                                return new ShowEventsCommand(wrapper);
                            case SHOW_PERF_LIST:
                                return new ShowPerformancesCommand(wrapper);
                            case SHOW_REG_FORM:
                                return new ShowRegistrationFormCommand();
                            case SHOW_LOGIN_FORM:
                                return new ShowLoginFormCommand();
                            case LOGIN:
                                return new LoginCommand(wrapper);
                            case REGISTRATOR:
                                return new RegistratorCommand(wrapper);
                            case LOGOUT:
                                return new LogoutCommand(wrapper);
                            default:
                                return new HelloCommand(wrapper);
                        }
                    case USER:
                        switch (commandEnum) {
                            case USER_PROFILE:
                                return new ShowUserProfileCommand();
                            case SHOW_EVENT_LIST:
                                return new ShowEventsCommand(wrapper);
                            case SHOW_PERF_LIST:
                                return new ShowPerformancesCommand(wrapper);
                            case SHOW_REG_FORM:
                                return new ShowRegistrationFormCommand();
                            case SHOW_LOGIN_FORM:
                                return new ShowLoginFormCommand();
                            case LOGIN:
                                return new LoginCommand(wrapper);
                            case REGISTRATOR:
                                return new RegistratorCommand(wrapper);
                            case LOGOUT:
                                return new LogoutCommand(wrapper);
                            default:
                                return new HelloCommand(wrapper);
                        }
                }
            } else {
                switch (commandEnum) {
                    case SHOW_EVENT_LIST:
                        return new ShowEventsCommand(wrapper);
                    case SHOW_PERF_LIST:
                        return new ShowPerformancesCommand(wrapper);
                    case SHOW_REG_FORM:
                        return new ShowRegistrationFormCommand();
                    case SHOW_LOGIN_FORM:
                        return new ShowLoginFormCommand();
                    case LOGIN:
                        return new LoginCommand(wrapper);
                    case REGISTRATOR:
                        return new RegistratorCommand(wrapper);
                    case LOGOUT:
                        return new LogoutCommand(wrapper);
                    default:
                        return new HelloCommand(wrapper);
                }
            }
        } catch (Exception t) {
            return new HelloCommand(wrapper);
        }

        return new HelloCommand(wrapper);
    }
}
