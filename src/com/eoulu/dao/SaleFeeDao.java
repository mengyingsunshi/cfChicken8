package com.eoulu.dao;

import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.SaleFee;
import com.eoulu.util.DBUtil;

public class SaleFeeDao {
	
	public List<Map<String, Object>> getDataByPage(Page page){
		DBUtil dbUtil = new DBUtil();
		String sql = "select ID,CustomerName,ServiceItem,VisitTime,SalesMan,Engineer,IsPass,OperatingTime,"
				+ "Recipient,CopyList,Password,Sender from t_sales_fee order by  OperatingTime desc limit ?,?";
		
		List<Map<String, Object>> list = dbUtil.QueryToList(sql,new Object[]{(page.getCurrentPage()-1)*page.getRows(),page.getRows()});
		return list;
	}
	
	public int getAllCount(){
		DBUtil dbUtil = new DBUtil();
		String sql = "select COUNT(ID) Count from t_sales_fee";
		
		List<Map<String, Object>> list = dbUtil.QueryToList(sql,null);
		int count = 0;
		if(list.size()>1){
			count = Integer.parseInt(list.get(1).get("Count").toString());
		}
		return count;
	}
	
	public int insert(SaleFee fee) throws NumberFormatException, Exception{
		DBUtil dbUtil = new DBUtil();
		String sql = "insert into t_sales_fee(CustomerName,ServiceItem,VisitTime,SalesMan,Engineer,IsPass,OperatingTime"
				+ ",Recipient,CopyList,Password,Sender) values(?,?,?,?,?,?,?,?,?,?,?)";
		
		Object[] param = new Object[11];
		param[0] = fee.getCustomerName();
		param[1] = fee.getServiceItem();
		param[2] = fee.getVisitTime();
		param[3] = fee.getSalesMan();
		param[4] = fee.getEngineer();
		param[5] = fee.getIsPass();
		param[6] = fee.getOperatingTime();
		param[7] = fee.getToStr();
		param[8] = fee.getCopyToStr();
		param[9] = fee.getPassword();
		param[10] = fee.getSender();
		
		int id = Integer.parseInt(dbUtil.insertGetId(sql, param).toString());
		return id;
		
	}
	
	public boolean update(SaleFee fee){
		DBUtil dbUtil = new DBUtil();
		String sql = "update t_sales_fee set CustomerName=?,ServiceItem=?,VisitTime=?,SalesMan=?,Engineer=?,OperatingTime=?,"
				+ "Recipient=?,CopyList=?,Password=? WHERE ID = ?";
				
		
		Object[] param = new Object[10];
		param[0] = fee.getCustomerName();
		param[1] = fee.getServiceItem();
		param[2] = fee.getVisitTime();
		param[3] = fee.getSalesMan();
		param[4] = fee.getEngineer();
		param[5] = fee.getOperatingTime();
		param[6] = fee.getToStr();
		param[7] = fee.getCopyToStr();
		param[8] = fee.getPassword();
		param[9] = fee.getID();
		
		int result = dbUtil.executeUpdate(sql, param);
		return result>0?true:false;
		
	}
	
	public boolean updateIsPass(String isPass,int ID){
		DBUtil dbUtil = new DBUtil();
		String sql = "update t_sales_fee set IsPass = ? where ID = ?";
		
		int result = dbUtil.executeUpdate(sql, new Object[]{isPass,ID});
		return result>0?true:false;
	}
	
	public List<Map<String, Object>> getInfoByID(int ID){
		DBUtil dbUtil = new DBUtil();
		String sql = "select CustomerName,ServiceItem,VisitTime,Engineer,Recipient,SalesMan,VisitTime from t_sales_fee where ID = ?";
		return dbUtil.QueryToList(sql, new Object[]{ID});
	
	}
	
	public boolean saveRecipient(int ID,String recipient){
		DBUtil dbUtil = new DBUtil();
		String sql = "update t_sales_fee set Recipient = ? where ID = ?";
		int result = dbUtil.executeUpdate(sql, new Object[]{recipient,ID});
		
		return result>0?true:false;
	}

}
