package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.SaleFeeService;
import com.eoulu.service.impl.SaleFeeServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class SaleFeeMailServlet
 */
@WebServlet("/SaleFeeMail")
public class SaleFeeMailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaleFeeMailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int ID = Integer.parseInt(request.getParameter("ID")==null?"0":request.getParameter("ID"));
		String isPass = request.getParameter("IsPass");
		String reason = request.getParameter("Reason");
		String user = request.getSession().getAttribute("email").toString();
		SaleFeeService service = new SaleFeeServiceImpl();
		response.getWriter().write(new Gson().toJson(service.review(ID, isPass, reason, user)));

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int ID = Integer.parseInt(request.getParameter("ID")==null?"0":request.getParameter("ID"));
		String toStr = request.getParameter("To");
		String copytoStr = request.getParameter("Copyto");
		String user = request.getSession().getAttribute("email").toString();
		String pwd = request.getParameter("Password");
		SaleFeeService service = new SaleFeeServiceImpl();

		response.getWriter().write(new Gson().toJson(service.applicationMail(ID, toStr, copytoStr, user, pwd)));
		

	}

}
