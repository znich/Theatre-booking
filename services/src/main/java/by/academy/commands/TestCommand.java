package by.academy.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class TestCommand implements ICommand {

	 private HttpServletRequest request;
	 private HttpServletResponse response;
	 
	public TestCommand(HttpServletRequest request, HttpServletResponse response){
		 this.request = request;
	     this.response = response;
	}
	@Override
	public String execute() throws ServletException, IOException {
		System.out.println("111");
		String testString = request.getParameter("editor1");
		testString +=request.getParameter("inputCategoryId");
		System.out.println(testString);
		return null;
	}

}
