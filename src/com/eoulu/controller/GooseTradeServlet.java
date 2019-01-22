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
import com.eoulu.entity.GooseTrade;
import com.eoulu.service.GooseMarketService;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.impl.GooseMarketServiceImpl;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class GooseTradeServlet
 */
@WebServlet("/GooseTrade")
public class GooseTradeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GooseTradeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GooseMarketService service = new GooseMarketServiceImpl();
		String dataType = request.getParameter("DataType");
		if(dataType.equals("page")){
			int currentPage = Integer.parseInt(request.getParameter("CurrentPage")==null?
					"1":request.getParameter("CurrentPage"));
			Page page = new Page();
			page.setCurrentPage(currentPage);
			page.setRows(10);
			page.setRecordCounts(service.getTradeCount());
			List<Map<String, Object>> list = service.getGooseTrade(page);
			Map<String, Object> map = new HashMap<>();
			map.put("data", list);
			map.put("pageCount", page.getPageCounts());
			map.put("currentPage", currentPage);
			response.getWriter().write(new Gson().toJson(map));
		}else if(dataType.equals("buy")){
			String email = request.getSession().getAttribute("email").toString();
			response.getWriter().write(new Gson().toJson(service.getBuyPageInfo(email)));
		}else if(dataType.equals("sell")){
			String email = request.getSession().getAttribute("email").toString();
			response.getWriter().write(new Gson().toJson(service.getTradableNum(email)));
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, Object> map = new HashMap<>();
		String operateType = request.getParameter("OperateType");
		String name = request.getParameter("Name");
		int number = 0;
		try{
			number = Integer.parseInt(request.getParameter("Number"));
		}catch(NumberFormatException e){
			map.put("status", false);
			map.put("message", "请填写正确的数字格式！");
			response.getWriter().write(new Gson().toJson(map));
			return;
		}
		GooseTrade trade = new GooseTrade();
		trade.setName(name);
		trade.setNumber(number);
		
		GooseMarketService service = new GooseMarketServiceImpl();
		LogInfoService service2 = new LogInfoServiceImpl();
		switch (operateType) {
		case "sell":
			map = service.addSellInfo(trade);
			if((boolean) map.get("status")){
				service2.insert(request, "鹅市", "添加卖出信息-"+name);
			}
			break;

		case "buy":
			map = service.addBuyInfo(trade);
			if((boolean) map.get("status")){
				service2.insert(request, "鹅市", "添加买入信息-"+name);
			}
			break;
		}
		response.getWriter().write(new Gson().toJson(map));
	}

}
