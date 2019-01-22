package com.eoulu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;
import com.eoulu.entity.LoanApplication;

public interface LoanApplicationService {

/**
 * 操作
 * @param application
 * @param type
 * @return
 */
	Map<String, Object> operateApplication(LoanApplication application,String type);

	/**
	 * 获取下一个申请编号
	 * @return
	 */
	String getApplicationNo();
	/**
	 * 分页查询
	 * @param page
	 * @param column1
	 * @param content1
	 * @param column2
	 * @param content2
	 * @return
	 */
	List<Map<String, Object>> getDataByPage(Page page,String column1,String content1,String column2,String content2,boolean filter);
	/**
	 * 记录数
	 * @param column1
	 * @param content1
	 * @param column2
	 * @param content2
	 * @return
	 */
	int getCount(String column1,String content1,String column2,String content2);
	/**
	 * 发申请邮件
	 * @param toList
	 * @param copyList
	 * @param content
	 * @param subject
	 * @return
	 */
	public boolean sendApplicationMail(HttpServletRequest request,String catelog);
	
	/**
	 * 批量发送催促邮件
	 * @param ids
	 * @param downLoadUrl
	 * @return
	 */
	public String sendPushMail(String ids,String downLoadUrl);
	/**
	 * 通过申请
	 * @param ID
	 * @return
	 */
	public boolean updateIsPass(int ID,String isPass,String reason);
	/**
	 * 预览界面货物信息
	 * @param loanID
	 * @return
	 */
	public List<Map<String, Object>> getGoods(int loanID);
	/**
	 * 导出表格
	 * @return
	 */
	public String exportExcel(String path);

}
