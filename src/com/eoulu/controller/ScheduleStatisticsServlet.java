package com.eoulu.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.ScheduleService;
import com.eoulu.service.impl.ScheduleServiceImpl;
import com.google.gson.Gson;


/**
 * Servlet implementation class ScheduleStatisticsServlet
 */
@WebServlet("/ScheduleStatistics")
public class ScheduleStatisticsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ScheduleStatisticsServlet() {
        super();
     
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("Type");
		ScheduleService service = new ScheduleServiceImpl();
		Map<String, Object> map = null;
		String start = "";
		String end = "";
		switch (type) {
		case "year":
			String year = request.getParameter("Year");
			map = service.statisticByYear(year);
			break;

		case "month":
			String month = request.getParameter("Month"); 
			start = month+"-01";
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = null;
			try {
				startDate = sdFormat.parse(start);
			} catch (ParseException e) {

				e.printStackTrace();
			}
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startDate);
			end = month+"-" + calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			map = service.statisticsByMonth(start, end);
			break;
			
		case "day":
			start = request.getParameter("Start");
			end = request.getParameter("End");
			map = service.statisticsByMonth(start, end);
			break;
		}
		response.getWriter().write(new Gson().toJson(map));
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
