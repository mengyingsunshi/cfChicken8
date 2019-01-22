package com.eoulu.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.GooseMarketService;
import com.eoulu.service.impl.GooseMarketServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class Goose
 */
@WebServlet("/GoosePrice")
public class GoosePriceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoosePriceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		GooseMarketService service = new GooseMarketServiceImpl();
		response.getWriter().write(new Gson().toJson(service.getDayStatistics()));
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
		double price = 0;
		try{
			price = Double.parseDouble(request.getParameter("TodayPrice"));
		}catch(NumberFormatException e){
			e.printStackTrace();
			map.put("status", false);
			map.put("message", "请填写正确的数字格式！");
			response.getWriter().write(new Gson().toJson(map));
			return;
		}
		GooseMarketService service = new GooseMarketServiceImpl();
		response.getWriter().write(new Gson().toJson(service.updatePrice(price)));
	}

}
