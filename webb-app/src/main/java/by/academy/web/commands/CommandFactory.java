package by.academy.web.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.academy.commands.HelloCommand;
import by.academy.commands.ShowRegistrationFormCommand;

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
        SHOW_EVENTS_LIST,
        TEST_COMMAND
    }
    public static ICommand createCommand(HttpServletRequest request, HttpServletResponse response) {
        ICommand command = null;
        System.out.println("factory");
        String commandStr = (String) request.getParameter("action");
        Commands commandEnum;
        try {
            commandEnum = Commands.valueOf(commandStr.toUpperCase());
            switch (commandEnum) {
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
                case SHOW_EVENTS_LIST:
                	command = new ShowEventsCommand(request, response);
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
        CommandsRights commandsRights = new CommandsRights();
        if (!commandsRights.checkRights(command.getRights(), request.getSession())) {
            command = new HelloCommand(request, response);
        }
        return command;
    }

    private static class CommandsRights {

        public boolean checkRights(List<String> rights, HttpSession session) {
            boolean flag = false;
            if (!rights.isEmpty()) {
                for (String right : rights) {
                    if (session.getAttribute(right) != null) {
                        flag = true;
                        break;
                    }
                }
            } else {
                flag = true;
            }
            return flag;
        }
    }
}
