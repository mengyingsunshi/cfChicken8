package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.LoanApplicationService;
import com.eoulu.service.impl.LoanApplicationServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class LoanApplicationReview
 */
@WebServlet("/LoanApplicationReview")
public class LoanApplicationReviewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoanApplicationReviewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int ID = Integer.parseInt(request.getParameter("ID")==null?"0":request.getParameter("ID"));
		String isPass = request.getParameter("isPass");
		String reason = request.getParameter("reason");
		LoanApplicationService service = new LoanApplicationServiceImpl();
		response.getWriter().write(new Gson().toJson(service.updateIsPass(ID,isPass,reason)));
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LoanApplicationService service = new LoanApplicationServiceImpl();

		
		response.getWriter().write(new Gson().toJson(service.sendApplicationMail(request,"push")));
	}

}
