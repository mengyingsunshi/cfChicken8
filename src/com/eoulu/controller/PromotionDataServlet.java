package com.eoulu.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.service.PromotionDataService;
import com.eoulu.service.impl.PromotionServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class PromotionDataServlet
 */
@WebServlet("/PromotionData")
public class PromotionDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PromotionDataServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loadType = request.getParameter("loadType")==null?"":request.getParameter("loadType");
		if(loadType.equals("preview")){
			String date = request.getParameter("date");
			String sheet = request.getParameter("sheet").trim();
			PromotionDataService service = new PromotionServiceImpl();
			response.getWriter().write(service.getPath(date, sheet));
		}else if(loadType.equals("date")){
			PromotionDataService service = new PromotionServiceImpl();

			int currentPage = request.getParameter("currentPage")==null?0:Integer.parseInt(request.getParameter("currentPage"));
			Page page = new Page();
			page.setCurrentPage(currentPage);
			page.setRows(10);
			page.setRecordCounts(service.getCounts());
			Map<String, Object> map = new HashMap<>();
			map.put("data", service.getDate(page));
			map.put("pageCount", page.getPageCounts());
			map.put("currentPage", currentPage);
			response.getWriter().write(new Gson().toJson(map));
		
			
		}
		else{
		
			request.getRequestDispatcher("WEB-INF/webData.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PromotionDataService service = new PromotionServiceImpl();
		response.getWriter().write(new Gson().toJson(service.uploadExcel(request)));
		
	}

}
