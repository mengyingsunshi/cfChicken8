package com.eoulu.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.QuoteSystemService;
import com.eoulu.service.impl.QuoteSystemServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class GetCommodityModelServlet
 */
@WebServlet("/GetCommodityModel")
public class GetCommodityModelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetCommodityModelServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		QuoteSystemService service = new QuoteSystemServiceImpl();
		String model = request.getParameter("Key");
		response.getWriter().write(new Gson().toJson(service.getCommodityModel(model)));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
