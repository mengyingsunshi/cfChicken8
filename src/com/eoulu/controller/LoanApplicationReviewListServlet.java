package com.eoulu.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;
import com.eoulu.service.LoanApplicationService;
import com.eoulu.service.impl.LoanApplicationServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class LoanApplicationReviewListServlet
 */
@WebServlet("/LoanApplicationReviewList")
public class LoanApplicationReviewListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoanApplicationReviewListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String currentPage = request.getParameter("CurrentPage");
		String column1 = request.getParameter("Column1");
		String content1 = request.getParameter("Content1");
		String column2 = request.getParameter("Column2");
		String content2 = request.getParameter("Content2");
				
				
		LoanApplicationService service = new LoanApplicationServiceImpl();
		Page page = new Page();
		page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
		page.setRows(10);
		page.setRecordCounts(service.getCount(column1, content1, column2, content2));
		List<Map<String, Object>> list = service.getDataByPage(page, column1, content1, column2, content2,true);
	
		response.getWriter().write(new Gson().toJson(list));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ids = request.getParameter("ids");
		String downLoadUrl = request.getServletContext().getRealPath("/") + "down\\";
		LoanApplicationService service = new LoanApplicationServiceImpl();
		response.getWriter().write(service.sendPushMail(ids, downLoadUrl));
		
	}

}
