package com.eoulu.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.dao.GooseMarketDao;
import com.eoulu.dao.StaffInfoDao;
import com.eoulu.entity.GooseMy;
import com.eoulu.entity.GooseTrade;
import com.eoulu.service.GooseMarketService;
import com.eoulu.util.DBUtil;

public class GooseMarketServiceImpl implements GooseMarketService{
	@Override
	public Map<String, Object> addMyGoose(GooseMy my){
		Map<String, Object> map = new HashMap<>();
		GooseMarketDao dao = new GooseMarketDao();
		if(dao.isExist(my.getName())){
			map.put("status",false);
			map.put("message","所填姓名已存在！");
			return map;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		my.setOperatingTime(sdf.format(new Date()));
		boolean result = dao.insertMyGoose(my);
		map.put("status", result);
		String msg = result?"添加成功":"添加失败";
		map.put("message", msg);
		return map;
	}

	@Override
	public Map<String, Object> updateMyGoose(GooseMy my) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		my.setOperatingTime(sdf.format(new Date()));
		boolean result = new GooseMarketDao().updateMyGoose(my);
		Map<String, Object> map = new HashMap<>();
		map.put("status", result);
		String msg = result?"修改成功":"修改失败";
		map.put("message", msg);
		return map;
	}

	@Override
	public List<Map<String, Object>> getOneMyGoose(String email) {
		String name = "NA";
		List<Map<String, Object>> list = new StaffInfoDao().getTelAndName(email);
		if(list.size()>1){
			name = list.get(1).get("StaffName").toString();
		}
		return new GooseMarketDao().getOneMyGoose(name);
	}

	@Override
	public List<Map<String, Object>> getAllMyGoose(Page page) {
		
		return new GooseMarketDao().getAllMyGoose(page);
	}

	@Override
	public int getAllMyGooseCount() {

		return new GooseMarketDao().getAllMyGooseCount();
	}

	@Override
	public Map<String, Object> updatePrice(double price) {
		boolean result;
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdFormat.format(new Date());
		GooseMarketDao dao = new GooseMarketDao();
		List<Map<String, Object>> list = dao.getDayStatistics(date);
		if(list.size()>1){
			result = dao.updatePrice(price, date);
		}else{
			result = dao.insertPrice(price, date);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("status", result);
		map.put("message", "操作成功");
		return map;
	}
	
	@Override
	public List<Map<String,Object>> getDayStatistics(){
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdFormat.format(new Date());
		GooseMarketDao dao = new GooseMarketDao();
		List<Map<String, Object>> list =  dao.getDayStatistics(date);
		if(list.size() == 1){
			List<Map<String, Object>> oList = dao.getOldPrice();
			if(oList.size()>1){
				String oldPrice = oList.get(1).get("TodayPrice").toString();
				if(!oldPrice.equals("--")){
					dao.insertPrice(Double.parseDouble(oldPrice), date);
					list = dao.getDayStatistics(date);
				}
			}
		}
		List<Map<String, Object>> total =  dao.getTotalBuyNum();
		int totalNum = 0;
		if(total.size()>1){
			totalNum = Integer.parseInt(total.get(1).get("Total").toString());
		}
		list.get(1).put("Total",totalNum );
		return list;
			
	}

	@Override
	public Map<String, Object> addSellInfo(GooseTrade trade) {

		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
		trade.setDate(sdFormat.format(new Date()));
		SimpleDateFormat sdFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String operatingTime = sdFormat2.format(new Date());
		trade.setOperatingTime(operatingTime);
		trade.setState("卖出");
		Map<String, Object> map = new HashMap<>();
		GooseMarketDao dao = new GooseMarketDao();
		List<Map<String, Object>> list = dao.getOneMyGoose(trade.getName());
		int tradableNum = Integer.parseInt(list.get(1).get("TradableNum").toString());
		if(tradableNum<trade.getNumber()){
			map.put("status", false);
			map.put("message", "您的剩余可交易鹅数不足！");
			return map;
		}
		
		DBUtil dbUtil = new DBUtil();
		Connection conn = dbUtil.getConnection();
	
		try {
			conn.setAutoCommit(false);
			dao.updateTradableNum(trade, dbUtil);
			dao.addGooseTrade(trade, dbUtil);
			conn.commit();
			map.put("status", true);
			map.put("message", "操作成功");
			return map;
			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			map.put("status", false);
			map.put("message", "操作失败");
			return map;
			
		}finally{
			dbUtil.closed();
		}
	}

	@Override
	public Map<String, Object> addBuyInfo(GooseTrade trade) {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
		trade.setDate(sdFormat.format(new Date()));
		SimpleDateFormat sdFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String operatingTime = sdFormat2.format(new Date());
		trade.setOperatingTime(operatingTime);
		trade.setState("买入");
		Map<String, Object> map = new HashMap<>();
		GooseMarketDao dao = new GooseMarketDao();
		DBUtil dbUtil = new DBUtil();

		Connection conn = dbUtil.getConnection();
		try {
			conn.setAutoCommit(false);
			dao.addGooseTrade(trade, dbUtil);
			conn.commit();
			map.put("status", true);
			map.put("message", "操作成功");
			return map;
			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			map.put("status", false);
			map.put("message", "操作失败");
			return map;
		}finally {
			dbUtil.closed();
		}

		
	}

	@Override
	public List<Map<String, Object>> getGooseTrade(Page page) {
		
		return new GooseMarketDao().getGooseTrade(page);
	}

	@Override
	public int getTradeCount() {

		return new GooseMarketDao().getTradeCount();
	}

	@Override
	public Map<String, String> getBuyPageInfo(String email) {
		Map<String, String> map = new HashMap<>();
		String name = "NA";
		String canBuyNum = null;
		List<Map<String, Object>> list = new StaffInfoDao().getTelAndName(email);
		if(list.size()>1){
			name = list.get(1).get("StaffName").toString();
		}
		List<Map<String, Object>> list2 = new GooseMarketDao().getOneMyGoose(name);
		if(list2.size()==1){
			canBuyNum = "无信息";
		}else{
			canBuyNum = new GooseMarketDao().getCanBuyNum();
		}
		map.put("name", name);
		map.put("canBuyNum", canBuyNum);
		return map;
	}

	@Override
	public  Map<String, Object> realBuying(GooseTrade trade) {
		GooseMarketDao dao = new GooseMarketDao();
		Map<String, Object> map = new HashMap<>();
		synchronized (GooseMarketServiceImpl.class) {
			List<Map<String, Object>> list = dao.getTradeInfoByID(trade.getID());
			int originNum = Integer.parseInt(list.get(1).get("Number").toString());
			if(trade.getNumber()>originNum){
				map.put("status", false);
				map.put("message", "剩余鹅数不足！");
				return map;
			}
			List<Map<String, Object>> myInfo = dao.getOneMyGoose(trade.getName());
			if(myInfo.size()==1){
				map.put("status", false);
				map.put("message", "请先录入您的我的鹅信息！");
				return map;
			}
			String seller = list.get(1).get("Name").toString();
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String operatingTime = sdFormat.format(new Date());
			trade.setOperatingTime(operatingTime);
			SimpleDateFormat sdFormat2 = new SimpleDateFormat("yyyy-MM-dd");
			trade.setDate(sdFormat2.format(new Date()));
			DBUtil dbUtil = new DBUtil();
			
			Connection conn = dbUtil.getConnection();
		
			try {
				conn.setAutoCommit(false);
				trade.setState("买入");
				dao.addDealRecord(trade, dbUtil);
				trade.setState("卖出");
				if(trade.getNumber() == originNum){
					trade.setState("成交");
				}
				dao.updateTradeNum(trade, dbUtil);
				//更新买方鹅数
				dao.updateBuyerPossessionNum(trade, dbUtil);
				trade.setName(seller);
				//更新卖方鹅数(被动卖)
				dao.updatePassivePossessionNum(trade, dbUtil);
				dao.updateSellStatistics(trade,dbUtil);
				conn.commit();
				map.put("status", true);
				map.put("message", "操作成功");
				return map;

			} catch (SQLException e) {
				e.printStackTrace();
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				map.put("status", false);
				map.put("message", "操作失败");
				return map;
				
			}finally{
				dbUtil.closed();
			}
		
		}
			
	}

	@Override
	public Map<String, Object> realSelling(GooseTrade trade) {
		GooseMarketDao dao = new GooseMarketDao();
		Map<String, Object> map = new HashMap<>();
		synchronized (GooseMarketServiceImpl.class) {
			List<Map<String, Object>> list = dao.getTradeInfoByID(trade.getID());
			int originNum = Integer.parseInt(list.get(1).get("Number").toString());
			if(trade.getNumber()>originNum){
				map.put("status", false);
				map.put("message", "超过剩余求购鹅数！");
				return map;
			}
			List<Map<String, Object>> myInfo = dao.getOneMyGoose(trade.getName());
			if(myInfo.size()==1){
				map.put("status", false);
				map.put("message", "请先录入您的我的鹅信息！");
				return map;
			}else{
				int possessionNum = Integer.parseInt(myInfo.get(1).get("PossessionNum").toString());
				if(possessionNum<trade.getNumber()){
					map.put("status", false);
					map.put("message", "您的剩余鹅数不足！");
					return map;
				}
			}
			String buyer = list.get(1).get("Name").toString();
	
			SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String operatingTime = sdFormat.format(new Date());
			trade.setOperatingTime(operatingTime);
			SimpleDateFormat sdFormat2 = new SimpleDateFormat("yyyy-MM-dd");
			trade.setDate(sdFormat2.format(new Date()));
			DBUtil dbUtil = new DBUtil();
			
			Connection conn = dbUtil.getConnection();

			try {
				conn.setAutoCommit(false);
				trade.setState("卖出");
				dao.addDealRecord(trade, dbUtil);
				trade.setState("买入");
				if(trade.getNumber() == originNum){
					trade.setState("成交");
				}
				dao.updateTradeNum(trade, dbUtil);
				//更新卖方鹅数（主动卖）
				dao.updateSellerPossessionNum(trade, dbUtil);
				//更新买方鹅数
				trade.setName(buyer);
				dao.updateBuyerPossessionNum(trade, dbUtil);
				dao.updateBuyStatistics(trade,dbUtil);
				conn.commit();
				map.put("status", true);
				map.put("message", "操作成功");
				return map;

			} catch (SQLException e) {
				e.printStackTrace();
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				map.put("status", false);
				map.put("message", "操作失败");
				return map;
				
			}finally {
				dbUtil.closed();
			}
		
		}
	
	}

	@Override
	public List<Map<String, Object>> getMonthStatistics(Page page) {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
		String today = sdFormat.format(new Date());
		sdFormat = new SimpleDateFormat("yyyy-MM");
		String startDate = sdFormat.format(new Date())+"-01";
		
		return new GooseMarketDao().getMonthStatistics(page, startDate, today);
	}

	@Override
	public int getMonthStatisticsCount() {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
		String today = sdFormat.format(new Date());
		sdFormat = new SimpleDateFormat("yyyy-MM");
		String startDate = sdFormat.format(new Date())+"-01";
		return new GooseMarketDao().getMonthStatisticsCount(startDate, today);
	}

	@Override
	public Map<String, String> getTradableNum(String email) {
		Map<String, String> map = new HashMap<>();
 		String name = "NA";
		List<Map<String, Object>> list = new StaffInfoDao().getTelAndName(email);
		if(list.size()>1){
			name = list.get(1).get("StaffName").toString();
		}
		String tradableNum = null;
		List<Map<String, Object>> list2 = new GooseMarketDao().getOneMyGoose(name);
		if(list2.size()==1){
			tradableNum = "无信息";
		}else{
			tradableNum = list2.get(1).get("TradableNum").toString();
		}
		map.put("name", name);
		map.put("tradableNum", tradableNum);
		
		return map;
	}

	@Override
	public List<Map<String, Object>> getDealRecord(int tradeID) {

		return new GooseMarketDao().getDealRecord(tradeID);
	}

}
