package by.academy.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;




import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 20.05.13
 * Time: 10:52
 * Фабрика, которая по запросу создаёт необходимую команду.
 */
public class CommandFactory {
    public enum Commands {
        LOGIN,
        REGISTRATOR,
        LOGOUT,
        SHOW_LOGIN_FORM,
        SHOW_REG_FORM,
        SHOW_PERF_LIST,
        SHOW_EVENT_LIST,
        USER_PROFILE,
        ADMIN_SHOW_PERFORMANCES,
        SHOW_EDIT_PERFORMANCE,
        EDIT_PERFORMANCE,
        TEST_COMMAND
    }
    public static ICommand createCommand(HttpServletRequest request, HttpServletResponse response) {
        ICommand command = null;
        System.out.println("factor-2");
        String commandStr = (String) request.getParameter("action");
        Commands commandEnum;
        try {
            commandEnum = Commands.valueOf(commandStr.toUpperCase());
            switch (commandEnum) {
                case USER_PROFILE:
                  //  command = new ShowUserProfileCommand(request, response);
                    break;
                case SHOW_EVENT_LIST:
                    command = new ShowEventsCommand(request, response);
                    break;
                case SHOW_PERF_LIST:
                        command = new ShowPerformancesCommand(request, response);
                    break;
                case SHOW_REG_FORM:
                    command = new ShowRegistrationFormCommand(request, response);
                    break;
                case SHOW_LOGIN_FORM:
                    command = new ShowLoginFormCommand(request, response);
                    break;
                case LOGIN:
                    command = new LoginCommand(request, response);
                    break;
                case REGISTRATOR:
                    command = new RegistratorCommand(request, response);
                    break;
                case LOGOUT:
                    command = new LogoutCommand(request, response);
                    break;
                case ADMIN_SHOW_PERFORMANCES:
                	command = new AdminShowPerformancesCommand (request, response);
                	break;
                case SHOW_EDIT_PERFORMANCE:
                	command = new ShowEditPerformanceCommand (request, response);
                	break;
                case EDIT_PERFORMANCE:
                	command = new EditPerformanceCommand (request, response);
                	break;
                case TEST_COMMAND:
                	command = new TestCommand(request,response);
                	break;
                default:
                    command = new HelloCommand(request, response);
                    break;
            }
        } catch (NullPointerException ex) {
            command = new HelloCommand(request, response);
        } catch (IllegalArgumentException ex) {
            command = new HelloCommand(request, response);
        }

        return command;
    }
}
