package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.OrderService;
import com.eoulu.service.impl.OrderServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class TransportRecordServlet
 */
@WebServlet("/TransportRecord")
public class TransportRecordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TransportRecordServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loadType = request.getParameter("LoadType");
		loadType = loadType==null?"":loadType;
		
		if(loadType.equals("data")){
			String year = request.getParameter("Year");
			year = year==null?"all":year;
			OrderService service = new OrderServiceImpl();
			response.getWriter().write(new Gson().toJson(service.getShippedCountStatistics(year)));
		}else {
			request.getRequestDispatcher("WEB-INF\\transportRecord.jsp").forward(request, response);
		} 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
