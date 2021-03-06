package com.eoulu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.Schedule;
import com.eoulu.util.DBUtil;

public class ScheduleDao {

	public List<Map<String, Object>> getAllData(Page page, String date) {
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql = "SELECT * FROM t_schedule where Date=? order by ID desc limit ?,?";

		Object[] param = new Object[] { date, (page.getCurrentPage()-1)*page.getRows(),page.getRows() };
		ls = db.QueryToList(sql, param);

		return ls;
	}

	public int getAllCounts(String date) {
		DBUtil db = new DBUtil();
		String sql = "select count(ID) ? from t_schedule where Date=? ";
		Object[] param = new Object[] { "AllCounts", date };
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		int counts = 0;
		if (ls.size() > 1) {
			counts = Integer.parseInt(ls.get(1).get(ls.get(0).get("col1")).toString());
		}
		return counts;
	}

	public boolean insert(Schedule factory) {
		DBUtil db = new DBUtil();
		SimpleDateFormat dfg = new SimpleDateFormat("yyyy-MM-dd");
		String sql = "insert into t_schedule (`Name`,CustomerUnit,ServiceItem,TransportTool,TrainNumber,Departure,Hotel,Date,Destination,DepartureDate,DestinationDate,OperatingTime,TravelDistance,HotelExpense,TrafficExpense) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] param = new Object[15];
		param[0] = factory.getName();
		param[1] = factory.getCustomerUnit();
		param[2] = factory.getServiceItem();
		param[3] = factory.getTransportTool();
		param[4] = factory.getTrainNumber();
		param[5] = factory.getDeparture();
		param[6] = factory.getHotel();
		param[7] = factory.getDate();
		param[8] = factory.getDestination();
		param[9] = factory.getDepartureDate();
		param[10] = factory.getDestinationDate();
		param[11] = dfg.format(new Date());
		param[12] = factory.getTravelDistance();
		param[13] = factory.getHotelExpense();
		param[14] = factory.getTrafficExpense();

		boolean flag = false;
		int temp = db.executeUpdate(sql, param);
		if (temp >= 1) {
			flag = true;
		}
		return flag;
	}

	public boolean update(Schedule factory) {
		DBUtil db = new DBUtil();
		SimpleDateFormat dfg = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "update t_schedule set `Name`=?,CustomerUnit=?,ServiceItem=?,TransportTool=?,TrainNumber=?,Departure=?,Hotel=?,Date=?,Destination=?,DepartureDate=?,DestinationDate=?,OperatingTime=?,TravelDistance=?,HotelExpense=?,TrafficExpense=? where ID=?";
		Object[] param = new Object[16];
		param[0] = factory.getName();
		param[1] = factory.getCustomerUnit();
		param[2] = factory.getServiceItem();
		param[3] = factory.getTransportTool();
		param[4] = factory.getTrainNumber();
		param[5] = factory.getDeparture();
		param[6] = factory.getHotel();
		param[7] = factory.getDate();
		param[8] = factory.getDestination();
		param[9] = factory.getDepartureDate();
		param[10] = factory.getDestinationDate();
		param[11] = dfg.format(new Date());
		param[12] = factory.getTravelDistance();
		param[13] = factory.getHotelExpense();
		param[14] = factory.getTrafficExpense();
		param[15] = factory.getID();
		boolean flag = false;
		int temp = db.executeUpdate(sql, param);
		if (temp >= 1) {
			flag = true;
		}
		return flag;
	}
	
	public boolean delete(int id){
		DBUtil db = new DBUtil();
		String sql = "delete from t_schedule where ID = ?";
		Object[] param = new Object[1];
		param[0] = id;
		boolean flag = false;
		int temp = db.executeUpdate(sql, param);
		if (temp >= 1) {
			flag = true;
		}
		return flag;
			
		
	}
	

	public List<Map<String, Object>> getPerson() {
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql = "SELECT Name from t_sales_representative";
		Object[] param = null;

		ls = db.QueryToList(sql, param);

		return ls;

	}

	public List<Map<String, Object>> getAllDataByName(String name, Page page) {
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql = "SELECT * FROM t_schedule where replace(Name,' ','') = replace(?,' ','') order by Date desc limit ?,?";
		Object[] param = new Object[] { name, (page.getCurrentPage()-1)*page.getRows(),page.getRows() };

		ls = db.QueryToList(sql, param);

		return ls;

	}

	public List<Map<String, Object>> getDateByName(String name) {
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql = "SELECT DISTINCT Date FROM t_schedule where replace(Name,' ','') = replace(?,' ','') ";
		Object[] param = new Object[] { name };

		ls = db.QueryToList(sql, param);

		return ls;

	}

	public int getCountsByName(String name) {
		DBUtil db = new DBUtil();
		String sql = "select count(ID) ? from t_schedule where replace(Name,' ','') = replace(?,' ','')";
		Object[] param = new Object[] { "AllCounts", name };
		List<Map<String, Object>> ls = db.QueryToList(sql, param);
		int counts = 0;
		if (ls.size() > 1) {
			counts = Integer.parseInt(ls.get(1).get(ls.get(0).get("col1")).toString());
		}
		return counts;
	}

	public  Map<String, Object> getDataByTime(String startTime, String endTime,String name) {
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		if (startTime.equals("0000-00-00") && endTime.equals("0000-00-00")) {
			String sql = "select COUNT(replace(Destination,'市','')) Count,replace(Destination,'市','') Destination from t_schedule where Destination<>'' GROUP BY (replace(Destination,'市','')) ORDER BY COUNT(Destination) DESC";

			Object[] param = null;
			ls = db.QueryToList(sql, param);
		} else if(name.equals("")){
			String sql = "select COUNT(replace(Destination,'市','')) Count,replace(Destination,'市','') Destination from t_schedule where Date BETWEEN ? AND ? AND Destination<>'' GROUP BY (replace(Destination,'市','')) ORDER BY COUNT(Destination) DESC";

			Object[] param = new Object[] { startTime, endTime };
			ls = db.QueryToList(sql, param);
		}else{
			String sql = "select COUNT(replace(Destination,'市','')) Count,replace(Destination,'市','') Destination from t_schedule where replace(Name,' ','') = replace(?,' ','') and Date BETWEEN ? AND ? AND Destination<>'' GROUP BY (replace(Destination,'市','')) ORDER BY COUNT(Destination) DESC";

			Object[] param = new Object[] {name, startTime, endTime };
			ls = db.QueryToList(sql, param);
		}

		Map<String, Object> map = new LinkedHashMap();
		if (ls.size() > 1) {
			for (int i = 0; i < ls.size(); i++) {
				if (ls.get(i).get("Destination") != null) {
					String key = ls.get(i).get("Destination").toString();
					String value = ls.get(i).get("Count").toString();
					map.put(key, value);
				}

			}
		}
//		System.out.println(map);
		return map;
	}

	public List<Map<String,Object>> getProvinceOrder (String startTime, String endTime,String name) {
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		if (startTime.equals("0000-00-00") && endTime.equals("0000-00-00")) {
			String sql = "SELECT t_provinces.province,COUNT(t_provinces.province) times FROM t_schedule,"
					+ "t_provinces WHERE LOCATE(t_schedule.Destination,t_provinces.province)>0 "
					+ "AND t_schedule.Destination != '' GROUP BY province UNION SELECT a.province,"
					+ "COUNT(a.province) times FROM t_schedule,(SELECT t_cities.city,t_provinces.province "
					+ "from t_cities LEFT JOIN t_provinces ON t_cities.provinceid = t_provinces.provinceid) a "
					+ "WHERE LOCATE(t_schedule.Destination,a.city)>0 AND t_schedule.Destination != ''  "
					+ "GROUP BY a.province ORDER BY times DESC";
			Object[] param = null;
			ls = db.QueryToList(sql, param);
		} else if(name.equals("")){
			String sql ="SELECT t_provinces.province,COUNT(t_provinces.province) times FROM t_schedule,"
					+ "t_provinces WHERE LOCATE(t_schedule.Destination,t_provinces.province)>0 "
					+ "AND t_schedule.Destination != '' AND (Date BETWEEN ? AND ?)GROUP BY province "
					+ "UNION SELECT a.province,COUNT(a.province) times FROM t_schedule,(SELECT t_cities.city,t_provinces.province "
					+ "from t_cities LEFT JOIN t_provinces ON t_cities.provinceid = t_provinces.provinceid) a "
					+ "WHERE LOCATE(t_schedule.Destination,a.city)>0 AND t_schedule.Destination != '' AND "
					+ "(Date BETWEEN ? AND ?) GROUP BY province ORDER BY times DESC";
			Object[] param = new Object[] { startTime, endTime,startTime, endTime };
			ls = db.QueryToList(sql, param);
		}else{
			String sql = "SELECT t_provinces.province,COUNT(t_provinces.province) times FROM t_schedule,"
					+ "t_provinces WHERE LOCATE(t_schedule.Destination,t_provinces.province)>0 "
					+ "AND t_schedule.Destination != '' AND replace(Name,' ','') = replace(?,' ','') AND "
					+ "Date BETWEEN ? AND ?GROUP BY province UNION SELECT a.province,"
					+ "COUNT(a.province) times FROM t_schedule,(SELECT t_cities.city,t_provinces.province "
					+ "from t_cities LEFT JOIN t_provinces ON t_cities.provinceid = t_provinces.provinceid) a "
					+ "WHERE LOCATE(t_schedule.Destination,a.city)>0 AND t_schedule.Destination != '' AND "
					+ "replace(Name,' ','') = replace(?,' ','') and Date BETWEEN ? AND ?  GROUP BY province ORDER BY times DESC";
			Object[] param = new Object[] { name, startTime, endTime, name, startTime, endTime };
			ls = db.QueryToList(sql, param);
		}
	
		return ls;
	}
	public List<Map<String,Object>> queryByCondition(String parameter,Object[] oj){
		DBUtil db = new DBUtil();
		String sql = "";
		if(parameter.equals("DepartureDate")||parameter.equals("DestinationDate")){
			sql = "SELECT * FROM t_schedule WHERE "+parameter+" =? order by Date desc limit ?,?";
		}else{
			if(oj.length == 2){
				sql = "SELECT * FROM t_schedule order by Date desc limit ?,?";
			}else{
				sql = "SELECT * FROM t_schedule WHERE replace("+parameter+",' ','') like replace(?,' ','') order by Date desc limit ?,?";
			}
		}
			
		List<Map<String,Object>> ls = db.QueryToList(sql, oj);
		return ls;
	}
	public int queryCounts(String parameter,Object[] oj){
		DBUtil db = new DBUtil();
		String sql = "";
		if(parameter.equals("DepartureDate")||parameter.equals("DestinationDate")){
			sql = "SELECT count(ID) FROM t_schedule WHERE "+parameter+" =? ";
		}else{
			if(oj == null){
				sql = "SELECT count(ID) FROM t_schedule";
			}else{
				sql = "SELECT count(ID) FROM t_schedule WHERE replace("+parameter+",' ','') like replace(?,' ','') order by ID";
			}
		}
	
		List<Map<String,Object>> ls = db.QueryToList(sql, oj);
		int count = 0;
		if(ls.size()>1){
			count = Integer.parseInt(ls.get(1).get("count(ID)").toString());
		}
		return count;
	}
	public List<Map<String, Object>> getDate(String parameter,Object[] oj) {
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql = "";
		if(parameter.equals("DepartureDate")||parameter.equals("DestinationDate")){
			sql = "SELECT DISTINCT Date FROM t_schedule WHERE "+parameter+" =? ";
		}else{
			if(oj == null){
				sql = "SELECT DISTINCT Date FROM t_schedule";
			}else{
				sql = "SELECT DISTINCT Date FROM t_schedule WHERE replace("+parameter+",' ','') like replace(?,' ','') ";
			}
		}
				

		ls = db.QueryToList(sql, oj);

		return ls;

	}
	public List<Map<String,Object>> getEngineer(){
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql =  "SELECT StaffName FROM (SELECT StaffName FROM t_staff  union " 
				+ "ALL SELECT Name StaffName  FROM t_schedule) t GROUP BY(REPLACE(StaffName,' ','')) "
				+ "ORDER BY COUNT(REPLACE(StaffName,' ','')) DESC";
		ls = db.QueryToList(sql, null);
		return ls;
	}
	
	public List<Map<String,Object>> getDistanceOrder(String startTime,String endTime){
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql = "select Name, SUM(TravelDistance) SumDistance from t_schedule  where Date BETWEEN ? AND ? GROUP BY (replace(Name,' ','')) ORDER BY SumDistance DESC";
		Object[] param = new  Object[]{startTime,endTime};
		ls = db.QueryToList(sql, param);
		return ls;
	}
	
	public List<Map<String,Object>> getFrequenceOrder(String startTime,String endTime){
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql = "select Name, Count(Name) times from t_schedule  where Date BETWEEN ? AND ? GROUP BY (replace(Name,' ','')) ORDER BY times DESC";
		Object[] param = new  Object[]{startTime,endTime};
		ls = db.QueryToList(sql, param);
		return ls;
	}
	
	public List<Map<String,Object>> getExpenseOrder(String startTime,String endTime){
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql = "select replace(Destination,'市','') Destination,SUM(HotelExpense+TrafficExpense) Expense from t_schedule " 
				+"WHERE Destination != '' AND Destination IS NOT NULL AND Date BETWEEN ? AND ? GROUP BY (replace(Destination,'市','')) ORDER BY Expense DESC";
		Object[] param = new  Object[]{startTime,endTime};
		ls = db.QueryToList(sql, param);
		return ls;
	}
	
	public List<Map<String,Object>> getCity(){
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql = "SELECT ID,Departure,Destination FROM t_schedule WHERE Departure != ''  AND Destination != ''";
		ls = db.QueryToList(sql, null);
		return ls;
	}
	public void updateDistance(List<Map<String,String>> list){
		DBUtil dbUtil= new DBUtil();
		Connection connection = dbUtil.getConnection();
		try {
			connection.setAutoCommit(false);

			String sql = "update t_schedule set TravelDistance=? where ID = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			for(int i = 0;i < list.size();i ++){
				statement.setFloat(1, Float.parseFloat(list.get(i).get("Distance")));
				statement.setInt(2, Integer.parseInt(list.get(i).get("ID")));
				statement.addBatch();
			}
			statement.executeBatch();
			connection.commit();
	
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
		}finally {
			dbUtil.closed();
		}
	}
	
	public List<Map<String, Object>> getDataByMonth(String start,String end){
		String sql = "SELECT DATE_FORMAT(Date,'%Y%m') months,COUNT(ID) number,"
				+ "SUM(IFNULL(TrafficExpense,0))+SUM(IFNULL(HotelExpense,0)) expense from t_schedule where Date >=? and Date <? "
				+ "GROUP BY months ";
		
		DBUtil db = new DBUtil();
		return db.QueryToList(sql, new Object[]{start,end});
	}
	
	public List<Map<String, Object>> getDataByDay(String start,String end){
		String sql = "SELECT DATE_FORMAT(Date,'%Y%m%d') days,COUNT(ID) number,"
				+ "SUM(IFNULL(TrafficExpense,0))+SUM(IFNULL(HotelExpense,0)) expense from t_schedule where Date >=? and Date <=? "
				+ "GROUP BY days ";
		
		DBUtil db = new DBUtil();
		return db.QueryToList(sql, new Object[]{start,end});
	}
	
	public List<Map<String, Object>> getDataByYear(String start){
		String sql = "SELECT DATE_FORMAT(Date,'%Y') years,COUNT(ID) number,"
				+ "SUM(IFNULL(TrafficExpense,0))+SUM(IFNULL(HotelExpense,0)) expense from t_schedule where Date >=? "
				+ "GROUP BY years ";
		
		DBUtil db = new DBUtil();
		return db.QueryToList(sql, new Object[]{start});
	}
	
	public boolean insertBySale(String date,String staffName,String customerName,String serviceItem){
		String sql = "insert into t_schedule(Date,Name,CustomerUnit,ServiceItem) values(?,?,?,?)";
		
		DBUtil dbUtil = new DBUtil();
		int result = dbUtil.executeUpdate(sql, new Object[]{date,staffName,customerName,serviceItem});
		
		return result>0?true:false;
	}
	
	public List<Map<String, Object>> getFeeBySale(String date,String engineer,String salesMan,String customerName,String serviceItem){
		String sql = "select SUM(IFNULL(HotelExpense,0))+SUM(IFNULL(TrafficExpense,0)) Fee from t_schedule "
				+ "where Date = ? and CustomerUnit = ? and ServiceItem = ? and (Name = ? or Name = ?)";
		
		DBUtil dbUtil = new DBUtil();
		Object[] param = new Object[5];
		param[0] = date;
		param[1] = customerName;
		param[2] = serviceItem;
		param[3] = engineer;
		param[4] = salesMan;
		
		return dbUtil.QueryToList(sql, param);
	}
	
	        
	
	
	

	
}
