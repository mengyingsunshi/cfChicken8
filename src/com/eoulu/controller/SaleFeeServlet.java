package com.eoulu.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.log.AccessStatistics;
import com.eoulu.service.SaleFeeService;
import com.eoulu.service.impl.SaleFeeServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class SaleFeeServlet
 */
@WebServlet("/SaleFee")
public class SaleFeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaleFeeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loadType = request.getParameter("LoadType") == null?"":request.getParameter("LoadType");
		if(loadType.equals("data")){
			String currentPage = request.getParameter("CurrentPage");
			SaleFeeService service = new SaleFeeServiceImpl();
			Page page = new Page();
			page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
			page.setRows(10);
			page.setRecordCounts(service.getAllCount());
			List<Map<String, Object>> list = service.getDataPyPage(page);
			
			Map<String, Object> map = new HashMap<>();
			map.put("data", list);
			map.put("pageCount", page.getPageCounts());
			map.put("currentPage", currentPage);
			response.getWriter().write(new Gson().toJson(map));
		}else{
			new AccessStatistics().operateAccess(request, "费用统计");
			request.getRequestDispatcher("WEB-INF//CostStatistics.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		SaleFeeService service = new SaleFeeServiceImpl();
		response.getWriter().write(new Gson().toJson(service.operate(request)));
	}

}
