package com.eoulu.service;

import java.util.List;
import java.util.Map;

import com.eoulu.commonality.Page;
import com.eoulu.entity.GooseMy;
import com.eoulu.entity.GooseTrade;

public interface GooseMarketService {
	/**
	 * 添加我的鹅信息
	 * @param my
	 * @return
	 */
	public Map<String, Object> addMyGoose(GooseMy my);
	
	/**
	 * 修改我的鹅信息
	 * @param my
	 * @return
	 */
	public Map<String, Object> updateMyGoose(GooseMy my);
	/**
	 * 查看我的鹅信息
	 * @param email
	 * @return
	 */
	public List<Map<String, Object>> getOneMyGoose(String email);
	/**
	 * 查看所有人我的鹅信息
	 * @param page
	 * @return
	 */
	public List<Map<String, Object>> getAllMyGoose(Page page);
	/**
	 * 获取我的鹅记录条数
	 * @return
	 */
	public int getAllMyGooseCount();
	/**
	 * 更改鹅价
	 * @param price
	 * @return
	 */
	public Map<String, Object> updatePrice(double price);
	/**
	 * 获取今日统计
	 * @return
	 */
	public List<Map<String, Object>> getDayStatistics();
	/**
	 * 挂出卖出信息
	 * @param trade
	 * @return
	 */
	public Map<String, Object> addSellInfo(GooseTrade trade);
	/**
	 * 挂出买入信息
	 * @param trade
	 * @return
	 */
	public Map<String, Object> addBuyInfo(GooseTrade trade);
	/**
	 * 分页查询贸易鹅记录
	 * @param page
	 * @return
	 */
	public List<Map<String, Object>> getGooseTrade(Page page);
	/**
	 * 贸易鹅记录条数
	 * @return
	 */
	public int getTradeCount();
	/**
	 * 获取买入界面相关信息
	 * @param email
	 * @return
	 */
	public Map<String, String> getBuyPageInfo(String email);
	/**
	 * 实际买入
	 * @param trade
	 * @return
	 */
	public Map<String, Object> realBuying(GooseTrade trade);
	/**
	 * 实际卖出
	 * @param trade
	 * @return
	 */
	public Map<String,Object> realSelling(GooseTrade trade);
	/**
	 * 本月统计
	 * @param page
	 * @return
	 */
	public List<Map<String, Object>> getMonthStatistics(Page page);
	/**
	 * 本月统计条数
	 * @return
	 */
	public int getMonthStatisticsCount();
	/**
	 * 获取可交易数
	 * @return
	 */
	public Map<String, String> getTradableNum(String email);
	/**
	 * 获取交易记录
	 * @param tradeID
	 * @return
	 */
	public List<Map<String, Object>> getDealRecord(int tradeID);
	


	
	
	
}
