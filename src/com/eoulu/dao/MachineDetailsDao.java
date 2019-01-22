package com.eoulu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.MachineDetails;
import com.eoulu.util.DBUtil;

public class MachineDetailsDao {

	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	public List<Map<String,Object>> getMachineDetails(Page page){
    
		List<Map<String, Object>> ls = null;
		DBUtil db = new DBUtil();
		String sql= "select t_machine_details.ID,t_customer.CustomerName CustomerUnit,t_customer.Contact CustomerName,t_machine_details.Model,"
				+ "t_machine_details.SN,t_machine_details.ContractNO,t_machine_details.InstalledTime,t_machine_details.CustomerID,"
				+ "t_machine_details.Status,t_machine_details.LatestProgress,t_machine_details.Responsible,t_machine_details.IsNormal "
				+ "from t_machine_details left join t_customer on t_customer.ID =t_machine_details.CustomerID "
				+ "order by InstalledTime desc,CASE WHEN Status IS NULL THEN 4 END limit ?,?";
			
		Object[] parameter = new Object[]{(page.getCurrentPage()-1)*page.getRows(),page.getRows()};

		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	/**
	 * 总数量
	 * @return
	 */
	public int getAllCounts(){
		int counts = 0;
		DBUtil db = new DBUtil();
		String sql = "select count(ID) ? from t_machine_details order by InstalledTime desc";
		
		Object[] parameter = new Object[]{"AllCounts"};
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);
		
		if(ls.size()>1)
			counts = Integer.parseInt(ls.get(1).get(ls.get(0).get("col1")).toString());
		
		return counts;
	}
	
	public List<Map<String, Object>> getCurrentProgress(int MachineID){
	    
		DBUtil dbUtil = new DBUtil();
		String sql= "select ID,CurrentProgress,Date from t_machine_progress where MachineID=? ORDER BY ID";

		
		return dbUtil.QueryToList(sql,new Object[]{MachineID});
	}
	
	
	public List<Map<String, Object>> getDetailDate(int machineId){
		DBUtil dbUtil = new DBUtil();
		String sql = "select TimeId,MachineId,Orders,StartDate,EndDate,Days from t_machine_date where MachineID=? ORDER BY Orders ";
		
		return dbUtil.QueryToList(sql, new Object[]{machineId});
	}
	


	/**
	 * 添加
	 * @param invoice
	 * @param db
	 * @return
	 * @throws Exception 
	 */
	public int insert(MachineDetails machine,DBUtil db) throws Exception{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Object[] parameter = new Object[10];
		String sql = "insert into t_machine_details (Model,SN,ContractNO,InstalledTime,"
				+ "OperatingTime,CustomerID,Status,LatestProgress,Responsible,IsNormal) values (?,?,?,?,?,?,?,?,?,?)";
	
		parameter[0] = machine.getModel();
		parameter[1] = machine.getSN();
		parameter[2] = machine.getContractNO();
		parameter[3] = machine.getInstalledTime();
		parameter[4] = df.format(new Date());
		parameter[5] = machine.getCustomerID();
		parameter[6] = machine.getStatus();
		parameter[7] = machine.getCurrentProgress();
		parameter[8] = machine.getResponsible();
		parameter[9] = machine.getIsNormal();
		
		int i = 0;
		i = Integer.parseInt(db.insertGetIdNotClose(sql, parameter).toString());

		return i;
	}
	
	public void insertDetailDate(List<Map<String, Object>> list,int machineID,DBUtil dbUtil) throws SQLException{
		
		String sql1 = "DELETE FROM t_machine_date WHERE MachineID=?";
	
		
		Connection conn = dbUtil.getConnection();
	
		dbUtil.executeUpdateNotClose(sql1, new Object[]{machineID});
		String sql = "insert into t_machine_date (MachineId,Orders,StartDate,EndDate,Days) values(?,?,?,?,?)";
		PreparedStatement statement = conn.prepareStatement(sql);
		for(int i = 0;i < list.size();i ++){
			statement.setInt(1, machineID);
			statement.setString(2, (String)list.get(i).get("Order"));
			statement.setString(3, (String)list.get(i).get("StartDate"));
			statement.setString(4, (String)list.get(i).get("EndDate"));
			statement.setInt(5, (int) list.get(i).get("Days"));
			statement.addBatch();
		}
		statement.executeBatch();
		
		
		
	}
	/**
	 * 修改
	 * @param packing
	 * @return
	 * @throws SQLException 
	 */
	public void update(MachineDetails machine,DBUtil db) throws SQLException{
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Object[] parameter = new Object[11];
		String sql = "update  t_machine_details set Model=?,SN=?,ContractNO=?,InstalledTime=?,"
				+ "OperatingTime=?,CustomerID=?,Status=?,LatestProgress=?,Responsible=?,IsNormal=? where ID=?";

		
		parameter[0] = machine.getModel();
		parameter[1] = machine.getSN();
		parameter[2] = machine.getContractNO();
		parameter[3] = machine.getInstalledTime();
		parameter[4] = df.format(new Date());
		parameter[5] = machine.getCustomerID();
		parameter[6] = machine.getStatus();
		parameter[7] = machine.getCurrentProgress();
		parameter[8] = machine.getResponsible();
		parameter[9] = machine.getIsNormal();
		parameter[10] = machine.getID();
		db.executeUpdateNotClose(sql, parameter);
	
	}
	
	public void insertProgress(List<Map<String, String>> list,int MachineID,DBUtil db) throws SQLException{

		String sql1 = "DELETE FROM t_machine_progress WHERE MachineID=?";
	
		
		Connection conn = db.getConnection();
	
		db.executeUpdateNotClose(sql1, new Object[]{MachineID});
		String sql = "INSERT INTO t_machine_progress(MachineID,CurrentProgress,Date) values(?,?,?)";
		PreparedStatement statement = conn.prepareStatement(sql);
		for(int i = 0;i < list.size();i ++){
			statement.setInt(1, MachineID);
			statement.setString(2, list.get(i).get("CurrentProgress"));
			statement.setString(3, list.get(i).get("Date"));
			statement.addBatch();
		}
		statement.executeBatch();
		
	}
	
	public boolean delete(int id){
		
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] param = new Object[]{id};
		String sql = "delete from t_machine_details where ID=?";
		int i = 0;
		i = db.executeUpdate(sql, param);
		if(i>=1){
			flag = true;
		}
		return flag;
	}
	/**
	 * 搜索
	 * @param sql
	 * @param parameter 
	 * @return
	 */
	public List<Map<String, Object>> getQueryList(String sql,Object[] parameter){
		
		DBUtil db = new DBUtil();
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);
		return ls;
		
	}
	
	/**
	 * 根据合同号获取合同页面的合同配置
	 */
	public List<Map<String,Object>> getConfigure(String ContractNO){
	    
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();


		String sql= "select t_order_info.ID,t_order_info.ExceptDate,t_order_info.StockNumber,t_order_info.LogisticsNumber,t_commodity_info.CommodityName Remarks,"
				+ "t_order_info.OrderID,t_commodity_info.Model EquipmentModel,t_order_info.Number,t_order_info.Date,t_order_info.DeliveryNumber,"
				+ "t_order_status.Status from t_order_info left join t_commodity_info on t_order_info.EquipmentModel=t_commodity_info.ID left join t_order_status on "
				+ "t_order_info.Status=t_order_status.ID left join t_order on t_order.ID = t_order_info.OrderID where t_order.ContractNo=?";
		Object[] parameter = new Object[]{ContractNO};

		ls = db.QueryToList(sql, parameter);
		return ls;
	}
	
	public List<Map<String, Object>> installTimeStatistics(String model,Connection conn){
		List<Map<String, Object>> list = new ArrayList<>();
		String sql = "SELECT b.Responsible,b.Totals FROM (SELECT Responsible,SUM(IFNULL(t_machine_date.Days,0)) Totals "
				+ "FROM t_machine_details LEFT JOIN t_machine_date ON t_machine_details.ID=t_machine_date.MachineId "
				+ "WHERE Model LIKE ? AND Responsible != ''  GROUP BY t_machine_details.ID)b WHERE Totals != 0";
		
		PreparedStatement statement = null;
		ResultSet rSet = null;
		
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, "%"+model+"%");
			rSet = statement.executeQuery();
			while (rSet.next()) {
				Map<String, Object> map = new HashMap<>();
				map.put("Totals", rSet.getInt(2));
				map.put("Responsible", rSet.getString(1));
				list.add(map);	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
		
		
	}
	
	public List<Map<String, Object>> acceptanceStatistics(){
		DBUtil dbUtil = new DBUtil();
		String sql = "SELECT a.Years,a.Num/b.Num Percent,b.Num TotalNum FROM "
				+ "(select COUNT(ID) Num,DATE_FORMAT(InstalledTime,'%Y') Years from "
				+ "t_machine_details where InstalledTime > '2019-01-01' AND IsNormal = 0 "
				+ "GROUP BY DATE_FORMAT(InstalledTime,'%Y'))a LEFT JOIN "
				+ "(select COUNT(ID) Num,DATE_FORMAT(InstalledTime,'%Y') Years from t_machine_details "
				+ "where InstalledTime > '2019-01-01' AND (IsNormal = 0 OR IsNormal = 1) "
				+ "GROUP BY DATE_FORMAT(InstalledTime,'%Y')) b ON a.Years = b.Years order by Years";
		return dbUtil.QueryToList(sql, null);
	
	}

	 
	
}
