package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.ScheduleService;
import com.eoulu.service.impl.ScheduleServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class ExpenseStatisticsServlet
 */
@WebServlet("/ExpenseStatistics")
public class ExpenseStatisticsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExpenseStatisticsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ScheduleService service = new ScheduleServiceImpl();
		String startTime = request.getParameter("startTime") == null?"":request.getParameter("startTime");
		String endTime = request.getParameter("endTime") == null?"":request.getParameter("endTime");
		response.getWriter().write(new Gson().toJson(service.getExpenseOrder(startTime, endTime)));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
