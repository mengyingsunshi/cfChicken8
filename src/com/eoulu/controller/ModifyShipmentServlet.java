package com.eoulu.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.service.ShipmentService;
import com.eoulu.service.impl.ShipmentServiceImpl;
import com.google.gson.Gson;
@WebServlet("/ModifyShipment")
public class ModifyShipmentServlet extends HttpServlet{

	
	private static final long serialVersionUID = 1L;
	public ModifyShipmentServlet(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ShipmentService service = new ShipmentServiceImpl();
		
		resp.getWriter().write(new Gson().toJson(service.updateShipment(req)));
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

}
