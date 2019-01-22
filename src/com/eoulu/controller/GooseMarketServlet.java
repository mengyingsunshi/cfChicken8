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
import com.eoulu.entity.GooseMy;
import com.eoulu.log.AccessStatistics;
import com.eoulu.service.GooseMarketService;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.impl.GooseMarketServiceImpl;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class GooseMarketServlet
 */
@WebServlet("/GooseMarket")
public class GooseMarketServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GooseMarketServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String loadType = request.getParameter("LoadType") == null?"":request.getParameter("LoadType");
		if(loadType.equals("data")){
			GooseMarketService service = new GooseMarketServiceImpl();
	
			String email = request.getSession().getAttribute("email").toString();
			String authority = "low";
			if((email.equals("luwenbo@eoulu.com"))||(email.equals("liuyanan@eoulu.com"))){
				authority = "high";
			}
				
			int currentPage = Integer.parseInt(request.getParameter("CurrentPage")==null?
					"1":request.getParameter("CurrentPage"));
			Page page = new Page();
			page.setCurrentPage(currentPage);
			page.setRows(10);
			List<Map<String, Object>> list = null;
			if(authority.equals("low")){
				page.setRecordCounts(1);
				list = service.getOneMyGoose(email);
			}else{
				page.setRecordCounts(service.getAllMyGooseCount());
				list = service.getAllMyGoose(page);
			}
			
			Map<String, Object> map = new HashMap<>();
			map.put("data", list);
			map.put("pageCount", page.getPageCounts());
			map.put("currentPage", currentPage);
			response.getWriter().write(new Gson().toJson(map));
		}else{
			new AccessStatistics().operateAccess(request, "鹅市");
			request.getRequestDispatcher("WEB-INF//equityMarket.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> map = new HashMap<>();
		String email = request.getSession().getAttribute("email").toString();
		if(!email.equals("luwenbo@eoulu.com")&&!email.equals("liuyanan@eoulu.com")){
			map.put("status",false);
			map.put("message","抱歉，您无此权限！");
			response.getWriter().write(new Gson().toJson(map));
			return;
		}
		
		String type = request.getParameter("Type");
		int ID = Integer.parseInt(request.getParameter("ID")==null?
				"0":request.getParameter("ID"));
		String Name = request.getParameter("Name");
		String Department = request.getParameter("Department");
		int PossessionNum = 0;
		int TradableNum = 0;
		try{
			PossessionNum = Integer.parseInt(request.getParameter("PossessionNum"));
			TradableNum = Integer.parseInt(request.getParameter("TradableNum"));
		}catch(NumberFormatException e){
			map.put("status", false);
			map.put("message", "请填写正确的数字格式！");
			response.getWriter().write(new Gson().toJson(map));
			return;
		}
		GooseMy my = new GooseMy();
		my.setID(ID);
		my.setName(Name);
		my.setDepartment(Department);
		my.setPossessionNum(PossessionNum);
		my.setTradableNum(TradableNum);
		GooseMarketService service = new GooseMarketServiceImpl();
		LogInfoService service2 = new LogInfoServiceImpl();
		switch (type) {
		case "add":
			map = service.addMyGoose(my);
			if((boolean) map.get("status")){
				service2.insert(request, "鹅市", "添加我的鹅信息-"+Name);
			}
			break;

		case "update":
			map = service.updateMyGoose(my);
			if((boolean) map.get("status")){
				service2.insert(request, "鹅市", "修改我的鹅信息-"+Name);
			}
			break;
		}
		response.getWriter().write(new Gson().toJson(map));
		
	}

}
