package com.eoulu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;

public interface SaleFeeService {
	
	public List<Map<String, Object>> getDataPyPage(Page page);
	
	public int getAllCount();
	
	public Map<String,Object> operate(HttpServletRequest request);
	/**
	 * 申请邮件
	 * @param ID
	 * @param toStr
	 * @param copytoStr
	 * @param sender
	 * @param pwd
	 * @return
	 */
	public boolean applicationMail(int ID,String toStr,String copytoStr,String sender,String pwd);
	
	/**
	 * 审批
	 * @param isPass
	 * @param reason
	 * @return
	 */
	public Map<String, Object> review(int ID,String isPass,String reason,String user);

}
