package com.eoulu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class VolumnDao {
	
	public void updateVolumn(Connection conn) {
		String sql1 = "delete from t_sale_volume";
		String sql2 = "INSERT INTO t_sale_volume(Model,UnitPrice,ProductCategory,Classify,Year_2016,Year_2017 ) "
				+ "SELECT x.Model,x.UnitPrice,x.ProductCategory,x.Classify,x.Sum Year_2016,y.Sum Year_2017 FROM "
				+ "(SELECT  a.Model,a.UnitPrice,a.ProductCategory,a.Classify,COALESCE(b.Sum,0) Sum  from (SELECT DISTINCT Model,CostPrice*1.5 UnitPrice, ProductCategory,"
				+ "Classify from t_commodity_info )a LEFT JOIN (select  SUM(COALESCE(t_order_info.Number,0)) Sum,Model "
				+ "from t_commodity_info LEFT JOIN t_order_info on t_commodity_info.ID = t_order_info.EquipmentModel "
				+ "LEFT JOIN t_order ON t_order_info.OrderID = t_order.ID WHERE DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')>= '2016-01-01' "
				+ "and DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')<= '2016-12-31' AND t_order.PageType = '0' GROUP BY t_commodity_info.Model)b "
				+ "ON a.Model = b.Model)x JOIN (SELECT  a.Model,COALESCE(b.Sum,0) Sum from (SELECT DISTINCT Model from t_commodity_info )a LEFT JOIN "
				+ "(select  SUM(COALESCE(t_order_info.Number,0)) Sum,Model from t_commodity_info LEFT JOIN t_order_info on t_commodity_info.ID = t_order_info.EquipmentModel "
				+ "LEFT JOIN t_order ON t_order_info.OrderID = t_order.ID  WHERE DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')>= '2017-01-01' and "
				+ "DATE_FORMAT(t_order.DateOfSign,'%Y-%m-%d')<= '2017-12-31' AND t_order.PageType = '0' GROUP BY t_commodity_info.Model)b "
				+ "ON a.Model = b.Model)y ON x.Model = y.Model  WHERE x.Sum != 0 OR y.Sum != 0 ORDER BY Year_2016 DESC ";
		try {
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(sql1);
			ps.executeUpdate();
			ps = conn.prepareStatement(sql2);
			ps.executeUpdate();
			conn.commit();
			
			
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
		
	}

}
