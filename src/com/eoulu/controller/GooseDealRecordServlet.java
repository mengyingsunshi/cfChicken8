package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.GooseMarketService;
import com.eoulu.service.impl.GooseMarketServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class GooseDealRecordServlet
 */
@WebServlet("/GooseDealRecord")
public class GooseDealRecordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GooseDealRecordServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int tradeID = Integer.parseInt(request.getParameter("TradeID")==null?"0":request.getParameter("TradeID"));
		
		GooseMarketService service = new GooseMarketServiceImpl();
		response.getWriter().write(new Gson().toJson(service.getDealRecord(tradeID)));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
