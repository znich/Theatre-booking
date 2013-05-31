

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Siarhei Poludvaranin
 * Date: 19.05.13
 * Time: 21:08
 * To change this template use File | Settings | File Templates.
 */
public interface ICommand {
    public List<String> getRights();
    public String execute() throws ServletException, IOException;
}
