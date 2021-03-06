package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.service.MachineDetailsService;
import com.eoulu.service.impl.MachineDetailsServiceImpl;
@WebServlet("/GetMachineDetailsByPageTwo")
public class GetMachineDetailsByPageTwoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public GetMachineDetailsByPageTwoServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MachineDetailsService service = new MachineDetailsServiceImpl();
		Page page = new Page();
		String currentPage = req.getParameter("currentPage");
		String classify1 = req.getParameter("type1");
		String parameter1 = req.getParameter("searchContent1").trim();
		String classify2 = req.getParameter("type2");
		String parameter2 = req.getParameter("searchContent2").trim();
		
		if(classify1.equals("项目状态")){
			switch (parameter1) {
			case "交付":
				parameter1 = "1"; 
				
				break;
			case "尾款":
				parameter1 = "2";
				break;
			case "完结":
				parameter1 = "3";
				break;

			default:
				break;
			}
		}
		
		if(classify2.equals("项目状态")){
			switch (parameter2) {
			case "交付":
				parameter2 = "1"; 
				
				break;
			case "尾款":
				parameter2 = "2";
				break;
			case "完结":
				parameter2 = "3";
				break;

			default:
				break;
			}
		}
		page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
		page.setRows(10);
		page.setRecordCounts(service.getCountByClassify(classify1, parameter1, classify2, parameter2));
		req.setAttribute("machine", service.getMachineDetailsByPageInTwo(classify1, parameter1, classify2, parameter2, page));
		req.setAttribute("currentPage", page.getCurrentPage());
		req.setAttribute("machineCounts", page.getRecordCounts());
		req.setAttribute("pageCounts", page.getPageCounts());
		req.setAttribute("classify1", classify1);
		req.setAttribute("parameter1", parameter1);
		req.setAttribute("classify2", classify2);
		req.setAttribute("parameter2", parameter2);
		req.setAttribute("queryType", "mixSelect");

		req.getRequestDispatcher("WEB-INF//machine.jsp").forward(req, resp);
		
	}
	

}
