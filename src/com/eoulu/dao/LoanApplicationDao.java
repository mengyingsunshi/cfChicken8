package com.eoulu.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eoulu.entity.LoanApplication;
import com.eoulu.util.DBUtil;

public class LoanApplicationDao {
	
	
	public List<Map<String, Object>> getDataByPage(String sql,Object[] param){
		
		return new DBUtil().QueryToList(sql, param);
		
		
	}
	
	public int getCount(String sql,Object[] param){
		int count = 0;
		List<Map<String, Object>> list = new DBUtil().QueryToList(sql, param);
		if(list.size()>1){
			count = Integer.parseInt(list.get(1).get("Count").toString());
		}
		return count;
		
	}
	
	public int insert(LoanApplication application,DBUtil db) throws Exception{
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "insert into t_loan_application(ApplicationDate,Applicant,ApplicationNo,IsReturn,Area,LoanDate,"
				+ "ExpectedReturnDate,ActualReturnDate,CustomerName,Department,Contact,Phone,Remarks,IsPass,OperatingTime,LoanAgreement) values"
				+ "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] param = new Object[16];
		param[0] = application.getApplicationDate();
		param[1] = application.getApplicant();
		param[2] = application.getApplicationNo();
		param[3] = application.getIsReturn();
		param[4] = application.getArea();
		param[5] = application.getLoanDate();
		param[6] = application.getExpectedReturnDate();
		param[7] = application.getActualReturnDate();
		param[8] = application.getCustomerName();
		param[9] = application.getDepartment();
		param[10] = application.getContact();
		param[11] = application.getPhone();
		param[12] = application.getRemarks();
		param[13] = "未审批";
		param[14] = sdFormat.format(new Date());
		param[15] = application.getLoanAgreement();
		
		int id = Integer.parseInt(db.insertGetIdNotClose(sql, param).toString());
		return id;
	}
	
	public boolean update(LoanApplication application,DBUtil db) throws SQLException{
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sql = "update t_loan_application set ApplicationDate=?,Applicant=?,ApplicationNo=?,IsReturn=?,Area=?,LoanDate=?,"
				+ "ExpectedReturnDate=?,ActualReturnDate=?,CustomerName=?,Department=?,Contact=?,Phone=?,Remarks=?,OperatingTime=?,LoanAgreement=? where ID=?";

		Object[] param = new Object[16];
		param[0] = application.getApplicationDate();
		param[1] = application.getApplicant();
		param[2] = application.getApplicationNo();
		param[3] = application.getIsReturn();
		param[4] = application.getArea();
		param[5] = application.getLoanDate();
		param[6] = application.getExpectedReturnDate();
		param[7] = application.getActualReturnDate();
		param[8] = application.getCustomerName();
		param[9] = application.getDepartment();
		param[10] = application.getContact();
		param[11] = application.getPhone();
		param[12] = application.getRemarks();
		param[13] = sdFormat.format(new Date());
		param[14] = application.getLoanAgreement();
		param[15] = application.getID();
		
		int result = db.executeUpdateNotClose(sql, param);
		return result > 0?true:false;
	}
	public boolean updateDocument(LoanApplication application,List<Map<String, Object>> goods){
		String sql = "update t_loan_application set ApplicationNo=?,LoanDate=?,"
				+ "ExpectedReturnDate=?,CustomerName=?,Department=?,Contact=?,Phone=? where ID=?";
		Object[] param = new Object[8];
		param[0] = application.getApplicationNo();
		param[1] = application.getLoanDate();
		param[2] = application.getExpectedReturnDate();
		param[3] = application.getCustomerName();
		param[4] = application.getDepartment();
		param[5] = application.getContact();
		param[6] = application.getPhone();
		param[7] = application.getID();
		DBUtil dbUtil = new DBUtil();
		Connection connection = dbUtil.getConnection();
		try {
			connection.setAutoCommit(false);
			dbUtil.executeUpdateNotClose(sql, param);
			deleteGoods(application.getID(), dbUtil);
			addGoods(goods, application.getID(), dbUtil);
			connection.commit();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}finally {
			dbUtil.closed();
		}

	}
	public boolean deleteGoods(int loanID,DBUtil db) throws SQLException{
		String sql = "delete from t_loan_model where LoanID = ?";
		int result = db.executeUpdateNotClose(sql, new Object[]{loanID});
		return result > 0?true:false;
		
	}
	
	public void addGoods(List<Map<String, Object>> goods,int loanID,DBUtil db) throws SQLException{
		String sql = "insert into t_loan_model(LoanID,Model,Description,Qty,SerialNumber) values(?,?,?,?,?)";
		for(int i = 0;i < goods.size();i++){
			Object[] param = new Object[5];
			param[0] = loanID;
			param[1] = goods.get(i).get("Model");
			param[2] = goods.get(i).get("Description");
			param[3] = goods.get(i).get("Qty");
			param[4] = goods.get(i).get("SerialNumber")==null?"":goods.get(i).get("SerialNumber");
			db.executeUpdateNotClose(sql, param);
		}
	}
	
	public List<Map<String, Object>> getTodayCount(String today){
		String sql = "select ApplicationNo from t_loan_application where ApplicationNo like ? order by ApplicationNo desc";
		return new DBUtil().QueryToList(sql, new Object[]{"LOAN"+today+"%"});
		
	}
	
	public boolean updateIsPass(int ID,String isPass){
		String sql = "update t_loan_application set IsPass = ? where ID = ?";
		DBUtil dbUtil = new DBUtil();
		int result = dbUtil.executeUpdate(sql, new Object[]{isPass,ID});
		return result>0?true:false;
		
	}
	
	public List<Map<String, Object>> getGoods(int loanID){
		String sql = "select Model,Description,Qty,SerialNumber from t_loan_model where LoanID = ? order by ID";
		return new DBUtil().QueryToList(sql, new Object[]{loanID});
	}
	
	public List<Map<String, Object>> getApplication(int loanID){
		String sql = "select CustomerName,Department,Contact,ApplicationNo,LoanDate,ExpectedReturnDate,Phone,Applicant,ApplicationDate from t_loan_application where ID = ?";
		return new DBUtil().QueryToList(sql, new Object[]{loanID});
	}
	
	public List<Map<String, Object>> getExcelData(){
		String sql = "select t_loan_application.*,t_loan_model.Model,t_loan_model.Description,t_loan_model.Qty from t_loan_application LEFT JOIN t_loan_model on t_loan_application.ID=t_loan_model.LoanID order by t_loan_application.ID desc ";
		
		return new DBUtil().QueryToList(sql, null);
	}
	
	
	
	

}
