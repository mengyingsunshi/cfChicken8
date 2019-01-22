package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.MachineDetailsService;
import com.eoulu.service.impl.MachineDetailsServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class MachineStatisticsServlet
 */
@WebServlet("/MachineStatistics")
public class MachineStatisticsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MachineStatisticsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loadType = request.getParameter("loadType")==null?"": request.getParameter("loadType");
		if(loadType.equals("data")){
			MachineDetailsService service = new MachineDetailsServiceImpl();
			response.getWriter().write(new Gson().toJson(service.statistics()));
		}else{
			request.getRequestDispatcher("/WEB-INF/installingStatistic.jsp").forward(request, response);
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
