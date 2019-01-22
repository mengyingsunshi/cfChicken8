package com.eoulu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;
import com.eoulu.dao.ScheduleDao;
import com.eoulu.entity.Schedule;
import com.eoulu.service.LogInfoService;
import com.eoulu.service.ScheduleService;

public class ScheduleServiceImpl implements ScheduleService {

	public static final Map<String, String> classify_map;

	static {
		Map<String, String> map = new HashMap<>();
		map.put("员工姓名", "Name");
		map.put("客户单位", "CustomerUnit");
		map.put("服务事项", "ServiceItem");
		map.put("交通工具", "TransportTool");
		map.put("出发地", "Departure");
		map.put("目的地", "Destination");
		map.put("车牌/车次/航班", "TrainNumber");
		map.put("酒店信息", "Hotel");
		map.put("出发时间", "DepartureDate");
		map.put("到达时间", "DestinationDate");
		
		map.put("", "");
		classify_map = map;
	}

	@Override
	public List<Map<String, Object>> getAllData(Page page, String date) {
		return new ScheduleDao().getAllData(page, date);
	}

	@Override
	public int getAllCounts(String date) {
		return new ScheduleDao().getAllCounts(date);
	}

	@Override
	public boolean insert(HttpServletRequest request) {

		String name = request.getParameter("Name") == null ? "" : request.getParameter("Name");
		String unit = request.getParameter("CustomerUnit") == null ? "" : request.getParameter("CustomerUnit");
		String item = request.getParameter("ServiceItem") == null ? "" : request.getParameter("ServiceItem");
		String tool = request.getParameter("TransportTool") == null ? "" : request.getParameter("TransportTool");
		String number = request.getParameter("TrainNumber") == null ? "" : request.getParameter("TrainNumber");
		String departure = request.getParameter("Departure") == null ? "" : request.getParameter("Departure");
		String hotel = request.getParameter("Hotel") == null ? "" : request.getParameter("Hotel");
		String date = request.getParameter("Date") == null ? "0000-00-00" : request.getParameter("Date");
		String destination = request.getParameter("Destination") == null ? "" : request.getParameter("Destination");
		String departureDate = request.getParameter("DepartureDate") == null ? "00:00"
				: request.getParameter("DepartureDate");
		String destinationDate = request.getParameter("DestinationDate") == null ? "00:00"
				: request.getParameter("DestinationDate");
		float TravelDistance = request.getParameter("TravelDistance") == null?0:Float.parseFloat(request.getParameter("TravelDistance"));
		int HotelExpense = request.getParameter("HotelExpense") == null?0:Integer.parseInt(request.getParameter("HotelExpense"));
		int TrafficExpense = request.getParameter("TrafficExpense") == null?0:Integer.parseInt(request.getParameter("TrafficExpense"));

	
		Schedule s = new Schedule();
		s.setName(name);
		s.setCustomerUnit(unit);
		s.setServiceItem(item);
		s.setTransportTool(tool);
		s.setTrainNumber(number);
		s.setDeparture(departure);
		s.setHotel(hotel);
		s.setDate(date);
		s.setDestination(destination);
		s.setDepartureDate(departureDate);
		s.setDestinationDate(destinationDate);
		s.setTravelDistance(TravelDistance);
		s.setHotelExpense(HotelExpense);
		s.setTrafficExpense(TrafficExpense);
		ScheduleDao dao = new ScheduleDao();
		boolean flag = dao.insert(s);
		if (flag) {
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "服务部-员工行程";
			String description = "新增-" + name;
			log.insert(request, JspInfo, description);
		}
		return flag;
	}

	@Override
	public boolean update(HttpServletRequest request) {
		int id = request.getParameter("ID") == null ? 0 : Integer.parseInt(request.getParameter("ID"));
		String name = request.getParameter("Name") == null ? "" : request.getParameter("Name");
		String unit = request.getParameter("CustomerUnit") == null ? "" : request.getParameter("CustomerUnit");
		String item = request.getParameter("ServiceItem") == null ? "" : request.getParameter("ServiceItem");
		String tool = request.getParameter("TransportTool") == null ? "" : request.getParameter("TransportTool");
		String number = request.getParameter("TrainNumber") == null ? "" : request.getParameter("TrainNumber");
		String departure = request.getParameter("Departure") == null ? "" : request.getParameter("Departure");
		String hotel = request.getParameter("Hotel") == null ? "" : request.getParameter("Hotel");
		String date = request.getParameter("Date") == null ? "0000-00-00" : request.getParameter("Date");
		String destination = request.getParameter("Destination") == null ? "" : request.getParameter("Destination");
		String departureDate = request.getParameter("DepartureDate") == null ? "00:00"
				: request.getParameter("DepartureDate");
		String destinationDate = request.getParameter("DestinationDate") == null ? "00:00"
				: request.getParameter("DestinationDate");
		float TravelDistance = request.getParameter("TravelDistance") == null?0:Float.parseFloat(request.getParameter("TravelDistance"));
		int HotelExpense = request.getParameter("HotelExpense") == null?0:Integer.parseInt(request.getParameter("HotelExpense"));
		int TrafficExpense = request.getParameter("TrafficExpense") == null?0:Integer.parseInt(request.getParameter("TrafficExpense"));

	
		Schedule s = new Schedule();
		s.setID(id);
		s.setName(name);
		s.setCustomerUnit(unit);
		s.setServiceItem(item);
		s.setTransportTool(tool);
		s.setTrainNumber(number);
		s.setDeparture(departure);
		s.setHotel(hotel);
		s.setDate(date);
		s.setDestination(destination);
		s.setDepartureDate(departureDate);
		s.setDestinationDate(destinationDate);
		s.setTravelDistance(TravelDistance);
		s.setHotelExpense(HotelExpense);
		s.setTrafficExpense(TrafficExpense);
		ScheduleDao dao = new ScheduleDao();
		boolean flag = dao.update(s);
		if (flag) {
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "服务部-员工行程";
			String description = "修改-" + name;
			log.insert(request, JspInfo, description);
		}
		return flag;
	}
	
	public boolean delete(HttpServletRequest request){
		int id = request.getParameter("ID") == null ? 0 : Integer.parseInt(request.getParameter("ID"));
		String name = request.getParameter("Name") == null ? "" : request.getParameter("Name");
		ScheduleDao dao = new ScheduleDao();
	
		boolean flag = dao.delete(id);
		if (flag) {
			LogInfoService log = new LogInfoServiceImpl();
			String JspInfo = "服务部-员工行程";
			String description = "删除-" + name;
			log.insert(request, JspInfo, description);
		}
		return flag;
		
		
	}

	public List<Map<String, Object>> getPerson() {
		return new ScheduleDao().getPerson();
	}

	@Override
	public List<Map<String, Object>> getAllDataByName(String name, Page page) {

		return new ScheduleDao().getAllDataByName(name, page);
	}

	@Override
	public List<Map<String, Object>> query(String classify, String param, Page page) {
		String parameter = "";
		Object[] obj = null;
		List<Map<String, Object>> ls = null;
		ScheduleDao dao = new ScheduleDao();
		parameter = classify_map.get(classify).toString();
		if(parameter.equals("DepartureDate")||parameter.equals("DestinationDate")){
			obj = new Object[] {param , (page.getCurrentPage() - 1) * page.getRows(),
					page.getRows() };
		}else{
			if(param.equals("所有人")){
				obj = new Object[]{(page.getCurrentPage() - 1) * page.getRows(),
					page.getRows() };
			}else{
				obj = new Object[] { "%" + param + "%", (page.getCurrentPage() - 1) * page.getRows(),
					page.getRows() };
			}
		}
		

		return dao.queryByCondition(parameter, obj);
	}

	@Override
	public int queryCounts(String param, String classify) {
		String parameter = "";
		Object[] obj = null;
		parameter = classify_map.get(classify).toString();
		if(parameter.equals("DepartureDate")||parameter.equals("DestinationDate")){
			obj = new Object[] { param };
		}else{
			if(param.equals("所有人")){
				obj = null;
			}else{
				obj = new Object[] { "%" + param + "%"};
			}
		}
		return new ScheduleDao().queryCounts(parameter, obj);
	}
	
	@Override
	public List<Map<String, Object>> getDate(String param, String classify) {
		String parameter = "";
		Object[] obj = null;
		parameter = classify_map.get(classify).toString();
		if(parameter.equals("DepartureDate")||parameter.equals("DestinationDate")){
			obj = new Object[] { param };
		}else{
			if(param.equals("所有人")){
				obj = null;
			}else{
				obj = new Object[] { "%" + param + "%"};
			}
		}
		
		return new ScheduleDao().getDate(parameter, obj);
	}

	@Override
	public int getCountsByName(String name) {
		return new ScheduleDao().getCountsByName(name);
	}

	@Override
	public List<Map<String, Object>> getDateByName(String name) {
		return new ScheduleDao().getDateByName(name);
	}

	@Override
	public Map<String, Object> getDataByTime(String startTime, String endTime,String name) {

		return new ScheduleDao().getDataByTime(startTime, endTime,name);
	}

	@Override
	public List<Map<String, Object>> getProvinceOrder(String startTime, String endTime,String name) {
		return new ScheduleDao().getProvinceOrder(startTime, endTime,name);
	}

	@Override
	public List<Map<String, Object>> getEngineer() {
		
		return new ScheduleDao().getEngineer();
	}

	public List<Map<String, Object>> getDistanceOrder(String startTime,String endTime) {
		return new ScheduleDao().getDistanceOrder(startTime, endTime);
	}
	
	public List<Map<String, Object>> getFrequenceOrder(String startTime,String endTime) {
		return new ScheduleDao().getFrequenceOrder(startTime, endTime);
	}
	
	public List<Map<String, Object>> getExpenseOrder(String startTime,String endTime) {
		return new ScheduleDao().getExpenseOrder(startTime, endTime);
	}

	@Override
	public Map<String, Object> statisticByYear(String year) {
		Map<String, Object> map = new HashMap<>();
		ScheduleDao dao = new ScheduleDao();
		List<Map<String, Object>> list = null;
		String start = "",end = "";
		String[] group = null;
		String[] number = null;
		String[] expense = null;
		if(year.equals("all")){
			start = "2017-01-01";
			list = dao.getDataByYear(start);
			
			if(list!=null){
				group = new String[list.size()-1];
				number = new String[list.size()-1];
				expense = new String[list.size()-1];
				for(int i = 1;i < list.size();i++){
					group[i - 1] = list.get(i).get("years").toString();
					number[i - 1] = list.get(i).get("number").toString();
					expense[i - 1] = list.get(i).get("expense").toString();
				}
			}
				
				
		}else{
			start = year+"-01-01";
			end = (Integer.parseInt(year)+1)+"-01-01";
			list = dao.getDataByMonth(start, end);
			if(list!=null){
				group = new String[list.size()-1];
				number = new String[list.size()-1];
				expense = new String[list.size()-1];
				for(int i = 1;i < list.size();i++){
					group[i - 1] = list.get(i).get("months").toString();
					number[i - 1] = list.get(i).get("number").toString();
					expense[i - 1] = list.get(i).get("expense").toString();
				}

			}
		}
		
		map.put("group", group);
		map.put("number", number);
		map.put("expense", expense);
		return map;
	}

	@Override
	public Map<String, Object> statisticsByMonth(String start,String end) {
		Map<String, Object> map = new HashMap<>();
		ScheduleDao dao = new ScheduleDao();
		
		List<Map<String, Object>> list = dao.getDataByDay(start, end);
		if(list!=null){
			String[] group = new String[list.size()-1];
			String[] number = new String[list.size()-1];
			String[] expense = new String[list.size()-1];
			for(int i = 1;i < list.size();i++){
				group[i - 1] = list.get(i).get("days").toString();
				number[i - 1] = list.get(i).get("number").toString();
				expense[i - 1] = list.get(i).get("expense").toString();
			}
			map.put("group", group);
			map.put("number", number);
			map.put("expense", expense);

		}
		return map;
	}
	

}
