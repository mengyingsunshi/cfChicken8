package com.eoulu.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.Quality;
import com.eoulu.util.DBUtil;

public class CertificateQualityDao {

	
	
	
	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	public List<Map<String,Object>> getCertificateQuantity(Page page){
    
		List<Map<String, Object>> ls = null;
		
		DBUtil db = new DBUtil();


		String sql= "select t_order.Customer,t_order.ContractTitle,t_certificate_quantity.ContractNO,t_certificate_quantity.Date,"
				+ "t_certificate_quantity.DCNO,t_certificate_quantity.ID,t_certificate_quantity.PONO "
				+ "from t_certificate_quantity left join t_order on t_certificate_quantity.ContractNO=t_order.ContractNo "
				+ "order by OperatingTime desc limit ?,?";
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
		String sql = "select count(ID) ? from t_certificate_quantity order by OperatingTime desc";
		
		Object[] parameter = new Object[]{"AllCounts"};
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);
		
		if(ls.size()>1)
			counts = Integer.parseInt(ls.get(1).get(ls.get(0).get("col1")).toString());
		
		return counts;
	}

	
	/**
	 * 添加
	 * @param invoice
	 * @param db
	 * @return
	 */
	public boolean insert(Quality quality){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[5];
		String sql = "insert into t_certificate_quantity (ContractNO,Date,DCNO,PONO,OperatingTime) values (?,?,?,?,?)";
		parameter[0] = quality.getContractNO();
		parameter[1] = quality.getDate();
		parameter[2] = quality.getDCNO();
		parameter[3] = quality.getPONO();
		parameter[4] = df.format(new Date());
		int i = 0;
		i = db.executeUpdate(sql, parameter);
		if(i>=1){
			flag = true;
		}
		return flag;
	}
	
	
	/**
	 * 修改
	 * @param packing
	 * @return
	 */
	public boolean updateCertificateQuantity(Quality quality){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DBUtil db = new DBUtil();
		boolean flag = false;
		Object[] parameter = new Object[6];
		String sql = "update t_certificate_quantity set ContractNO=?,Date=?,DCNO=?,PONO=?,OperatingTime=? where ID=?";

		parameter[0]= quality.getContractNO();
		parameter[1]= quality.getDate();
		parameter[2]= quality.getDCNO();
		parameter[3]= quality.getPONO();
		parameter[4]= df.format(new Date());
		parameter[5]= quality.getID();
		
		
		int i = 0;
		i = db.executeUpdate(sql, parameter);
		if(i>=1){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public boolean delete(int id){
		boolean flag = false;
		DBUtil db = new DBUtil();
		String sql = "delete from t_certificate_quantity where ID=?";
		Object[] parameter = new Object[]{id};
		int i = 0;
		i = db.executeUpdate(sql, parameter);
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
	public List<Map<String, Object>> getCertificateQuantity(String sql,Object[] parameter){
		
		DBUtil db = new DBUtil();
		List<Map<String, Object>> ls = db.QueryToList(sql, parameter);
	
		return ls;
		
	}
	
	
}
