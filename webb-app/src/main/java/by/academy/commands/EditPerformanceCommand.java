package by.academy.commands;

import java.io.IOException;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.academy.Model.CategoryData;
import by.academy.Model.TicketsPriceData;
import by.academy.logic.AdminLogic;
import by.academy.logic.SiteLogic;
import by.academy.util.MessagesProperties;
import by.academy.util.PathProperties;

public class EditPerformanceCommand implements ICommand {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private SiteLogic siteLogic = new SiteLogic();
	private AdminLogic adminLogic = new AdminLogic();
	private HttpSession session = null;
	private final String MENU_ITEM_ATTRIBUTE = "menuItem";
	private final String PERFORMANCES_ATTRIBUTE = "performances";
	private final String ANSWER_ATTRIBUTE = "answer";
	private final String MESSAGE_ATTRIBUTE = "message";
	private final String PERFORMANCE_ANSWER_ATTRIBUTE = "editPerformanceList";
	private final String INPUT_LANG_ID = "inputLangId";
	private final String PERFORMANCE_ID_ATTRIBUTE = "performanceId";
	private final String INPUT_NAME_ATTRIBUTE = "inputName";
	private final String INPUT_SHORTDESCRIPTION_ATTRIBUTE = "inputShortDescription";
	private final String INPUT_DESCRIPTION_ATTRIBUTE = "inputDescription";
	private final String INPUT_IMAGE_ATTRIBUTE = "inputImage";
	private final String INPUT_DATE_INTERVAL = "inputDateInteval";
	private final String INPUT_CATEGORY_ATTRIBUTE = "inputCategoryId";
	private final String DATE_FORMAT = "MM/dd/yyyy";
	private final String TICKETS_PRICE_ATTRIBUTE = "ticketsPriceList";
	private final String INPUT_TICKETS_PRICE_ATTRIBUTE = "inputPrice";


	public EditPerformanceCommand(HttpServletRequest request,
			HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	@Override
	public String execute() throws ServletException, IOException {

		session = request.getSession();
		String inputLangId = request.getParameter(INPUT_LANG_ID);

		Integer langId = null;

		if (inputLangId != null) {

			langId = Integer.parseInt(inputLangId);
		}
		if (langId == null) {

			langId = 1;
		}

		Date date1 = null;
		Date date2 = null;
		String dateFirst;
		String dateLast;

		String dateInterval = request.getParameter(INPUT_DATE_INTERVAL);

		if (dateInterval != null && dateInterval.length() > 0) {
			String[] dates = dateInterval.split(" - ");
			dateFirst = dates[0];
			dateLast = dates[1];

			try {
				date1 = new SimpleDateFormat(DATE_FORMAT).parse(dateFirst);
				date2 =  new SimpleDateFormat(DATE_FORMAT).parse(dateLast);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			date1 = new Date();
			date2 = new Date();
		}
	
		java.sql.Date sqlDate1 = new java.sql.Date(date1.getTime()) ;
		java.sql.Date sqlDate2 = new java.sql.Date(date2.getTime()) ;
			
		Integer performanceId = 0;
		String idOfPerformance = request
				.getParameter(PERFORMANCE_ID_ATTRIBUTE);
		if (idOfPerformance.length()>0){ 
			performanceId = Integer.parseInt(idOfPerformance);}		
		Integer categoryId = Integer.parseInt(request
				.getParameter(INPUT_CATEGORY_ATTRIBUTE));
		String name = request.getParameter(INPUT_NAME_ATTRIBUTE);
		String shortDescription = request
				.getParameter(INPUT_SHORTDESCRIPTION_ATTRIBUTE);
		String description = request.getParameter(INPUT_DESCRIPTION_ATTRIBUTE);
		String image = request.getParameter(INPUT_IMAGE_ATTRIBUTE);
		
		List<TicketsPriceData> ticketsPrices = new ArrayList<TicketsPriceData>();
		ticketsPrices =  (List<TicketsPriceData>) session.getAttribute(TICKETS_PRICE_ATTRIBUTE);

		System.out.println(ticketsPrices.size());

		for (TicketsPriceData ticketsPrice : ticketsPrices){
			ticketsPrice.setPrice(Integer.parseInt(request.getParameter(INPUT_TICKETS_PRICE_ATTRIBUTE+ticketsPrice.getPriceCategory())));
			}

		CategoryData category = siteLogic.getCategoryById(categoryId, langId);
		
		boolean flag = adminLogic.editPerformance(performanceId, name, shortDescription, description, sqlDate1, sqlDate2, image, category, langId);
		String message = null;
		
		if (flag){
			flag = adminLogic.editTicketsPriceForPerformance(ticketsPrices);			
		}
		if (flag){
			message = MessagesProperties.createPathProperties().getProperties(MessagesProperties.REGISTER_SUCCESSFUL, "ru");
		}
		
		request.setAttribute(MENU_ITEM_ATTRIBUTE, PERFORMANCES_ATTRIBUTE);
		request.setAttribute(ANSWER_ATTRIBUTE, PERFORMANCE_ANSWER_ATTRIBUTE);
		request.setAttribute(MESSAGE_ATTRIBUTE,message) ;
		
		
		return PathProperties.createPathProperties().getProperty(
				PathProperties.ADMIN_PAGE);
	}
}