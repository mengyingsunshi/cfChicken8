package com.eoulu.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.GooseMy;
import com.eoulu.entity.GooseTrade;
import com.eoulu.util.DBUtil;

public class GooseMarketDao {
	
	public boolean insertMyGoose(GooseMy my){
		String sql = "insert into t_goose_my (Name,Department,PossessionNum,TradableNum,"
				+ "OperatingTime) values(?,?,?,?,?)";
		DBUtil dbUtil = new DBUtil();
		Object[] param = new Object[5];
		param[0] = my.getName();
		param[1] = my.getDepartment();
		param[2] = my.getPossessionNum();
		param[3] = my.getTradableNum();
		param[4] = my.getOperatingTime();
		int result = dbUtil.executeUpdate(sql, param);
		
		return result > 0?true:false;
	}
	
	public boolean isExist(String name){
		String sql = "select ID from t_goose_my where Name = ?";
		DBUtil dbUtil = new DBUtil();
		List<Map<String, Object>> list = dbUtil.QueryToList(sql, new Object[]{name});
		if(list.size()>1){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean updateMyGoose(GooseMy my){
		String sql = "update t_goose_my set Name=?,Department=?,PossessionNum=?,TradableNum=?,"
				+ "OperatingTime=? where ID = ?";
		DBUtil dbUtil = new DBUtil();
		Object[] param = new Object[6];
		param[0] = my.getName();
		param[1] = my.getDepartment();
		param[2] = my.getPossessionNum();
		param[3] = my.getTradableNum();
		param[4] = my.getOperatingTime();
		param[5] = my.getID();
		int result = dbUtil.executeUpdate(sql, param);
		
		return result > 0?true:false;
	}
	
	public List<Map<String, Object>> getOneMyGoose(String name){
		String sql = "select * from t_goose_my where Name = ?";
		DBUtil dbUtil = new DBUtil();
		return dbUtil.QueryToList(sql, new Object[]{name});
	}
	
	public List<Map<String, Object>> getAllMyGoose(Page page){
		String sql = "select * from t_goose_my order by ID desc limit ?,?";
		DBUtil dbUtil = new DBUtil();
		return dbUtil.QueryToList(sql, new Object[]{(page.getCurrentPage()-1)*page.getRows(),page.getRows()});
	}
	
	public int getAllMyGooseCount(){
		String sql = "select count(ID) Count from t_goose_my ";
		DBUtil dbUtil = new DBUtil();
		List<Map<String, Object>> list = dbUtil.QueryToList(sql, null);
		return Integer.parseInt(list.get(1).get("Count").toString());
	}
	
	public boolean updatePrice(double price,String date){
		String sql = "update t_goose_statistics set TodayPrice = ? where Date = ?";
		Object[] param = new Object[]{price,date};
		DBUtil dbUtil = new DBUtil();
		int result = dbUtil.executeUpdate(sql, param);
		return result > 0?true:false;
		
	}
	
	public boolean insertPrice(double price,String date){
		String sql = "insert into t_goose_statistics(TodayPrice,BuyToday,SellToday,Date) values (?,?,?,?)";
		Object[] param = new Object[]{price,0,0,date};
		DBUtil dbUtil = new DBUtil();
		int result = dbUtil.executeUpdate(sql, param);
		return result > 0?true:false;
	}
	
	public List<Map<String, Object>> getDayStatistics(String date){
		String sql = "select ID,TodayPrice,BuyToday,SellToday,OperatingTime from t_goose_statistics "
				+ "where Date = ?";
		DBUtil dbUtil = new DBUtil();
		return dbUtil.QueryToList(sql, new Object[]{date});
		
	}
	
	public List<Map<String, Object>> getMonthStatistics(Page page,String startDate,String endDate){
		String sql = "select ID,TodayPrice,BuyToday,SellToday,OperatingTime,Date,TodayPrice*(BuyToday+SellToday) volumeOfTotal from t_goose_statistics "
				+ "where Date >= ? and Date <= ? order by Date limit ?,?";
		DBUtil dbUtil = new DBUtil();
		return dbUtil.QueryToList(sql, new Object[]{startDate,endDate,
				(page.getCurrentPage()-1)*page.getRows(),page.getRows()});
		
	}
	
	public int getMonthStatisticsCount(String startDate,String endDate){
		String sql = "select COUNT(ID) Count from t_goose_statistics "
				+ "where Date >= ? and Date <= ?";
		DBUtil dbUtil = new DBUtil();
		List<Map<String, Object>> list = dbUtil.QueryToList(sql, new Object[]{startDate,endDate});
		return Integer.parseInt(list.get(1).get("Count").toString());
		
	}
	
	public List<Map<String, Object>> getOldPrice(){
		String sql = "select TodayPrice from t_goose_statistics where Date =  (select MAX(Date) from t_goose_statistics)";
		DBUtil dbUtil = new DBUtil();
		return dbUtil.QueryToList(sql, null);
	}
	
	public boolean addGooseTrade(GooseTrade trade,DBUtil dbUtil) throws SQLException{
		String sql = "insert into t_goose_trade (Name,State,Number,OperatingTime) values(?,?,?,?)";
		Object[] parameter = new Object[4];
		parameter[0] = trade.getName();
		parameter[1] = trade.getState();
		parameter[2] = trade.getNumber();
		parameter[3] = trade.getOperatingTime();
		int result = dbUtil.executeUpdateNotClose(sql, parameter);
		return result > 0?true:false;
	}
	
	public boolean updateTradableNum(GooseTrade trade,DBUtil dbUtil) throws SQLException{
		String sql = "update t_goose_my set TradableNum = TradableNum - ?,OperatingTime=? where Name = ?";
		int result = dbUtil.executeUpdateNotClose(sql, new Object[]{trade.getNumber(),
				trade.getOperatingTime(),trade.getName()});
		return result > 0?true:false;

	}
	
	public boolean updateSellStatistics(GooseTrade trade,DBUtil dbUtil) throws SQLException{
		String sql = null;
		Object[] param = null;
		if(getDayStatistics(trade.getDate()).size()>1){
			sql = "update t_goose_statistics set SellToday = SellToday + ?,OperatingTime = ? where  Date = ?";
			param = new Object[]{trade.getNumber(),trade.getOperatingTime(),trade.getDate()};
		}else{
			sql = "insert into t_goose_statistics (BuyToday,SellToday,OperatingTime,Date) values(?,?,?,?)";
			param = new Object[4];
			param[0] = 0;
			param[1] = trade.getNumber();
			param[2] = trade.getOperatingTime();
			param[3] = trade.getDate();

		}
		int result = dbUtil.executeUpdateNotClose(sql, param);
		return result > 0?true:false;
	}
	
	public boolean updateBuyStatistics(GooseTrade trade,DBUtil dbUtil) throws SQLException{

		String sql = null;
		Object[] param = null;
		if(getDayStatistics(trade.getDate()).size()>1){
			sql = "update t_goose_statistics set BuyToday = BuyToday + ?,OperatingTime = ? where  Date = ?";
			param = new Object[]{trade.getNumber(),trade.getOperatingTime(),trade.getDate()};
		}else{
			sql = "insert into t_goose_statistics (BuyToday,SellToday,OperatingTime,Date) values(?,?,?,?)";
			param = new Object[4];
			param[0] = trade.getNumber();
			param[1] = 0;
			param[2] = trade.getOperatingTime();
			param[3] = trade.getDate();

		}
		int result = dbUtil.executeUpdateNotClose(sql, param);
		return result > 0?true:false;
		
	}
	
	public List<Map<String, Object>> getGooseTrade(Page page){
		String sql = "select * from t_goose_trade order by OperatingTime desc limit ?,?";
		DBUtil dbUtil = new DBUtil();
		return dbUtil.QueryToList(sql, new Object[]{(page.getCurrentPage()-1)*page.getRows(),page.getRows()});
		
	}
	
	public int getTradeCount(){
		String sql = "select count(ID) Count from t_goose_trade ";
		DBUtil dbUtil = new DBUtil();
		List<Map<String, Object>> list = dbUtil.QueryToList(sql, null);
		return Integer.parseInt(list.get(1).get("Count").toString());
	}
	
	public String getCanBuyNum(){
		String sql = "select SUM(Number) Num from t_goose_trade where State = '卖出'";
		DBUtil dbUtil = new DBUtil();
		List<Map<String, Object>> list = dbUtil.QueryToList(sql, null);
		String num = list.get(1).get("Num").toString();
		if(num.equals("--")){
			num = "0";
		}
		return num;
	}
	//买
	public boolean updateBuyerPossessionNum(GooseTrade trade,DBUtil dbUtil) throws SQLException{
		String sql = "update t_goose_my set PossessionNum = PossessionNum + ?,TradableNum = TradableNum + ?,OperatingTime=? where Name = ?";
		
		int result = dbUtil.executeUpdateNotClose(sql, new Object[]{trade.getNumber(),trade.getNumber(),
				trade.getOperatingTime(),trade.getName()});
		return result > 0?true:false;
	}
	//主动卖
	public boolean updateSellerPossessionNum(GooseTrade trade,DBUtil dbUtil) throws SQLException{
		String sql = "update t_goose_my set PossessionNum = PossessionNum - ?,TradableNum = TradableNum - ?,OperatingTime=? where Name = ?";
		
		int result = dbUtil.executeUpdateNotClose(sql, new Object[]{trade.getNumber(),trade.getNumber(),
				trade.getOperatingTime(),trade.getName()});
		return result > 0?true:false;
	}
	//被动卖
	public boolean updatePassivePossessionNum(GooseTrade trade,DBUtil dbUtil) throws SQLException{
		String sql = "update t_goose_my set PossessionNum = PossessionNum - ?,OperatingTime=? where Name = ?";
		
		int result = dbUtil.executeUpdateNotClose(sql, new Object[]{trade.getNumber(),
				trade.getOperatingTime(),trade.getName()});
		return result > 0?true:false;
	}
	
	
	//更新交易信息里的剩余鹅数
	public boolean updateTradeNum(GooseTrade trade,DBUtil dbUtil) throws SQLException{
		String sql = "update t_goose_trade set Number = Number - ?,OperatingTime = ?,State = ? where ID= ?";
		
		Object[] param = new Object[4];
		param[0] = trade.getNumber();
		param[1] = trade.getOperatingTime();
		param[2] = trade.getState();
		param[3] = trade.getID();
		int result = dbUtil.executeUpdateNotClose(sql, param);
		return result > 0?true:false;
	}
	public List<Map<String, Object>> getTradeInfoByID(int ID){
		
		String sql = "select Name,Number from t_goose_trade where ID = ?";
		DBUtil dbUtil = new DBUtil();
		return dbUtil.QueryToList(sql, new Object[]{ID});
	}
	
	public boolean addDealRecord(GooseTrade trade,DBUtil dbUtil) throws SQLException{
		String sql = "insert into t_goose_deal (TradeID,Name,Number,OperateType,OperatingTime,Date) values(?,?,?,?,?,?)";
		
		Object[] param = new Object[6];
		param[0] = trade.getID();
		param[1] = trade.getName();
		param[2] = trade.getNumber();
		param[3] = trade.getState();
		param[4] = trade.getOperatingTime();
		param[5] = trade.getDate();
		int result = dbUtil.executeUpdateNotClose(sql, param);
		return result > 0?true:false;
	}
	
	public List<Map<String, Object>> getDealRecord(int tradeID){
		String sql = "select ID,Name,Number,OperateType,OperatingTime from t_goose_deal where TradeID = ?";
		DBUtil dbUtil = new DBUtil();
		
		return dbUtil.QueryToList(sql, new Object[]{tradeID});
	}
	
	public List<Map<String, Object>> getTotalTradableNum(){
		
		String sql = "select SUM(TradableNum) Total from t_goose_my ";
		DBUtil dbUtil = new DBUtil();
		
		return dbUtil.QueryToList(sql, null);
			
	}
	
	public List<Map<String, Object>> getTotalBuyNum(){
		String sql = "select SUM(Number) Total from t_goose_trade ";
		
		DBUtil dbUtil = new DBUtil();
		return dbUtil.QueryToList(sql, null);
	}

}
