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
import com.eoulu.entity.LoanApplication;
import com.eoulu.log.AccessStatistics;
import com.eoulu.service.LoanApplicationService;
import com.eoulu.service.impl.LoanApplicationServiceImpl;
import com.google.gson.Gson;

/**
 * Servlet implementation class LoanApplicationServlet
 */
@WebServlet("/LoanApplication")
public class LoanApplicationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoanApplicationServlet() {
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
			String column1 = request.getParameter("Column1");
			String content1 = request.getParameter("Content1");
			String column2 = request.getParameter("Column2");
			String content2 = request.getParameter("Content2");
					
					
			LoanApplicationService service = new LoanApplicationServiceImpl();
			Page page = new Page();
			page.setCurrentPage(currentPage==null?1:Integer.parseInt(currentPage));
			page.setRows(10);
			page.setRecordCounts(service.getCount(column1, content1, column2, content2));
			List<Map<String, Object>> list = service.getDataByPage(page, column1, content1, column2, content2,false);
			Map<String, Object> map = new HashMap<>();
			map.put("data", list);
			map.put("pageCount", page.getPageCounts());
			map.put("currentPage", currentPage);
			response.getWriter().write(new Gson().toJson(map));
		}else{
			new AccessStatistics().operateAccess(request, "借用申请");
			request.getRequestDispatcher("WEB-INF//LoanApplication.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int ID = Integer.parseInt(request.getParameter("ID")==null?"0":request.getParameter("ID"));
		String ApplicationDate = request.getParameter("ApplicationDate");
		String Applicant = request.getParameter("Applicant").trim();
		String ApplicationNo = request.getParameter("ApplicationNo");
		String IsReturn = request.getParameter("IsReturn");
	
		String Area = request.getParameter("Area");
		String LoanDate = request.getParameter("LoanDate");
		String ExpectedReturnDate = request.getParameter("ExpectedReturnDate");
		String ActualReturnDate = request.getParameter("ActualReturnDate");
		String CustomerName = request.getParameter("CustomerName").trim();
		String Department = request.getParameter("Department");
		String Contact = request.getParameter("Contact").trim();
		String Phone = request.getParameter("Phone").trim();
		
		String Remarks = request.getParameter("Remarks");
		String GoodsJson = request.getParameter("GoodsJson");
		String type = request.getParameter("Type");
		String LoanAgreement = request.getParameter("LoanAgreement");
		System.out.println("文件"+LoanAgreement);
		LoanApplication application = new LoanApplication();
		application.setID(ID);
		application.setApplicant(Applicant);
		application.setApplicationDate(ApplicationDate);
		application.setApplicationNo(ApplicationNo);
		application.setIsReturn(IsReturn);
		application.setArea(Area);
		application.setLoanDate(LoanDate);
		application.setExpectedReturnDate(ExpectedReturnDate);
		application.setActualReturnDate(ActualReturnDate);
		application.setCustomerName(CustomerName);
		application.setDepartment(Department);
		application.setContact(Contact);
		application.setPhone(Phone);
		application.setRemarks(Remarks);
		application.setGoodsJson(GoodsJson);
		application.setLoanAgreement(LoanAgreement);
		
		LoanApplicationService service = new LoanApplicationServiceImpl();
	
		response.getWriter().write(new Gson().toJson(service.operateApplication(application, type)));
		
		
	}

}
