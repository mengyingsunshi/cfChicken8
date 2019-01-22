package com.eoulu.dao;

import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.util.DBUtil;

public class PromotionDataDao {
	
	public boolean delete(String date){
	
		String sql = "delete from t_promotion_data where Date = ?";
		DBUtil dbUtil = new DBUtil();
		int result = dbUtil.executeUpdate(sql, new Object[]{date});
		return result > 0?true:false;
	}
	
	public boolean add(String fileName,String date){
		DBUtil dbUtil = new DBUtil();
		String sql = "insert into t_promotion_data(FileName,Date) values(?,?)";
		int result = dbUtil.executeUpdate(sql, new Object[]{fileName,date});
		
		return result > 0?true:false;
		
	}
	
	public List<Map<String, Object>> getFileName(String date){
		String sql = "select FileName from t_promotion_data where Date = ?";
		DBUtil dbUtil = new DBUtil();
		return dbUtil.QueryToList(sql, new Object[]{date});
	}
	
	public List<Map<String, Object>> getDateList(Page page){
		String sql = "select ID,Date from t_promotion_data order by Date desc  limit ?,?";
		Object[] param = new Object[]{(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		return  new DBUtil().QueryToList(sql, param);
	}
	
	public int getCounts(){
		DBUtil dbUtil = new DBUtil();
		String sql = "select COUNT(ID) Count from t_promotion_data";
		List<Map<String, Object>> list = dbUtil.QueryToList(sql, null);
		int count = 0;
		if(list.size()>1){
			count = Integer.parseInt(list.get(1).get("Count").toString());
		}
		return count;
	}

}
