package com.eoulu.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.eoulu.entity.VersionManagement;
import com.eoulu.util.DBUtil;

public class VersionManagementDao {
	
	public List<Map<String, Object>> getData(String ProjectName){
		DBUtil dbUtil = new DBUtil();
		String sql = "select ID,ProjectName,Version,Registrant,BoardingTime,UpdatedContent,VersionFile "
				+ "from t_version_management where ProjectName = ?";
		
		return dbUtil.QueryToList(sql, new Object[]{ProjectName});
	}
	
	public boolean addVersion(VersionManagement version){
		DBUtil dbUtil = new DBUtil();
		Connection conn = dbUtil.getConnection();
		try {
			conn.setAutoCommit(false);
			String sql = "update t_version_management set VersionFile = null where ProjectName = ?";

			String sql1 = "insert into t_version_management(ProjectName,Version,Registrant,BoardingTime,"
					+ "UpdatedContent,VersionFile,FilePath) values(?,?,?,?,?,?,?)";
			Object[] param = new Object[7];
			param[0] = version.getProjectName();
			param[1] = version.getVersion();
			param[2] = version.getRegistrant();
			param[3] = version.getBoardingTime();
			param[4] = version.getUpdatedContent();
			param[5] = version.getVersionFile();
			param[6] = version.getFilePath();
			dbUtil.executeUpdateNotClose(sql, new Object[]{version.getProjectName()});
			dbUtil.executeUpdateNotClose(sql1, param);
			conn.commit();
			return true;
			
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}finally {
			dbUtil.closed();
		}
	
	}
	
	public List<Map<String, Object>> getPath(String fileName){
		
		String sql = "select FilePath from t_version_management where VersionFile = ?";
		return new DBUtil().QueryToList(sql, new Object[]{fileName});
	}
	
	

}
