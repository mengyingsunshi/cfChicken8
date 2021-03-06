package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.entity.Invoice;
import com.eoulu.service.InvoiceService;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.impl.InvoiceServiceImpl;
import com.eoulu.service.impl.LogInfoServiceImpl;
import com.google.gson.Gson;


@WebServlet("/ModifyInvoice")
public class ModifyInvoiceServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public ModifyInvoiceServlet(){
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InvoiceService invoiceService = new InvoiceServiceImpl();
		Invoice invoice = new Invoice();
		
		invoice.setID(Integer.parseInt(request.getParameter("ID")));
		invoice.setContractNO(request.getParameter("ContractNO"));
		invoice.setAirPort(request.getParameter("AirPort"));
		invoice.setApplicant(request.getParameter("Applicant"));
		invoice.setDate(request.getParameter("Date").toString().equals("")?"0000-00-00":request.getParameter("Date").toString());
		invoice.setDCNO(request.getParameter("DCNO"));
		invoice.setDeparture(request.getParameter("Departure"));
		invoice.setDepartureDate(request.getParameter("DepartureDate").toString().equals("--")?"0000-00-00":request.getParameter("DepartureDate").toString());
		invoice.setDestination(request.getParameter("Destination"));
		invoice.setEndUser(request.getParameter("EndUser"));
		invoice.setPacking(request.getParameter("Packing"));
		invoice.setInvoiceNO(request.getParameter("InvoiceNO"));
		invoice.setManufacturer(request.getParameter("Manufacturer"));
		invoice.setProduct(request.getParameter("Product"));
		invoice.setOrigin(request.getParameter("Origin"));
		invoice.setOtherReference(request.getParameter("OtherReference"));
		invoice.setPaymentRemark(request.getParameter("PaymentRemark"));
		invoice.setPONO(request.getParameter("PONO"));
		invoice.setShippingMark(request.getParameter("ShippingMark"));
		invoice.setType(Integer.parseInt(request.getParameter("Type")));
		invoice.setVessel(request.getParameter("Vessel"));
		invoice.setAdd(request.getParameter("Add"));
		invoice.setTelFax(request.getParameter("TelFax"));
		invoice.setTotalAmount(Double.parseDouble(request.getParameter("TotalAmount"))); 
		invoice.setNinePaid(Double.parseDouble(request.getParameter("NinePaid")));
		invoice.setTenPaid(Double.parseDouble(request.getParameter("TenPaid")));
		if(invoiceService.InvoiceModify(invoice,request)>0){
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "发票页面";
			String description = "修改-"+request.getParameter("ContractNO");
			log.insert(request, JspInfo, description);
			response.getWriter().write("修改成功");
		}else{
			response.getWriter().write(new Gson().toJson("修改失败"));
		}
	}
}
