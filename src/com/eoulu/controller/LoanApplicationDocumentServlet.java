package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.LoanApplicationService;
import com.eoulu.service.impl.LoanApplicationServiceImpl;
import com.eoulu.syn.ExportLoanApplication;
import com.google.gson.Gson;

/**
 * Servlet implementation class LoanApplicationDocumentServlet
 */
@WebServlet("/LoanApplicationDocument")
public class LoanApplicationDocumentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoanApplicationDocumentServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    //添加面板上的申请编号
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String dataType = request.getParameter("dataType")==null?"":request.getParameter("dataType");
		LoanApplicationService service = new LoanApplicationServiceImpl();
		if(dataType.equals("goods")){
			int loanID = Integer.parseInt(request.getParameter("ID"));
			response.getWriter().write(new Gson().toJson(service.getGoods(loanID)));
			
		}else{
			response.getWriter().write(service.getApplicationNo());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("Type");
		if(type.equals("download")){
			ExportLoanApplication util = new ExportLoanApplication();
			util.setByRequest(request);
			String file = util.export(request.getServletContext().getRealPath("/") + "down\\");
			int beginIndex = file.indexOf("down");
			response.getWriter().write(file.substring(beginIndex).replace("\\", "/"));
		}else if(type.equals("send")){
			LoanApplicationService service = new LoanApplicationServiceImpl();
			response.getWriter().write(new Gson().toJson(service.sendApplicationMail(request,"application")));
			
		}
	}

}
