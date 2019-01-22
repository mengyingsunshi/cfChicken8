package com.eoulu.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;
import com.eoulu.dao.SaleFeeDao;
import com.eoulu.dao.ScheduleDao;
import com.eoulu.dao.StaffInfoDao;
import com.eoulu.entity.SaleFee;
import com.eoulu.service.SaleFeeService;
import com.eoulu.util.BaseEncrypt;
import com.eoulu.util.JavaMailToolsUtil;
import com.eoulu.util.MethodUtil;
import com.eoulu.util.SendMailUtil;

public class SaleFeeServiceImpl implements SaleFeeService{

	@Override
	public List<Map<String, Object>> getDataPyPage(Page page) {
		SaleFeeDao dao = new SaleFeeDao();
		List<Map<String, Object>> list = dao.getDataByPage(page);
		for(int i = 1;i < list.size();i ++){

			Map<String,Object> map = list.get(i);
			if(map.get("IsPass").toString().equals("已通过")){
				String date = map.get("VisitTime").toString();
				String customerName =  map.get("CustomerName").toString();
				String serviceItem = map.get("ServiceItem").toString();
				String salesMan = map.get("SalesMan").toString();
				String engineer = map.get("Engineer").toString();
				int fee = 0;
				List<Map<String, Object>> feeList =	new ScheduleDao().getFeeBySale(date, engineer, salesMan, customerName, serviceItem);
				if(!feeList.get(1).get("Fee").toString().equals("--")){
					fee = Integer.parseInt(feeList.get(1).get("Fee").toString())+1500;
				}
				map.put("Fee", fee);
			}else{
				map.put("Fee",0);
			}
			String enPassword = map.get("Password").toString();
			try {
				map.put("Password",BaseEncrypt.Decrypt(enPassword, "7418529637418529"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		return list;
	}

	@Override
	public Map<String, Object> operate(HttpServletRequest request) {
		
		String CustomerName = request.getParameter("CustomerName");
		String ServiceItem = request.getParameter("ServiceItem");
		String VisitTime = request.getParameter("VisitTime");
		String SalesMan = request.getParameter("SalesMan").trim();
		String Engineer = request.getParameter("Engineer").trim();
		int ID = Integer.parseInt(request.getParameter("ID")==null?"0":request.getParameter("ID"));
		
		String toStr = request.getParameter("To");
		String copytoStr = request.getParameter("Copyto");
		String user = request.getSession().getAttribute("email").toString();
		String pwd = request.getParameter("Password");
		String type = request.getParameter("Type");
		SaleFee fee = new SaleFee();
		fee.setCustomerName(CustomerName);
		fee.setEngineer(Engineer);
		fee.setID(ID);
		fee.setSalesMan(SalesMan);
		fee.setServiceItem(ServiceItem);
		fee.setVisitTime(VisitTime);
		fee.setCopyToStr(copytoStr);
		fee.setToStr(toStr);
		fee.setSender(user);
		try {
			fee.setPassword(BaseEncrypt.Encrypt(pwd, "7418529637418529"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		SaleFeeDao dao = new SaleFeeDao();
		Map<String, Object> map = new HashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		fee.setOperatingTime(sdf.format(new Date()));
		boolean status = false;
		String msg = "";
		switch (type) {
		case "add":
			fee.setIsPass("未审批");
			try {
				ID = dao.insert(fee);
			} catch (Exception e) {
				e.printStackTrace();
			}
			status = ID>0?true:false;
			map.put("status", status);
			//map.put("message", status?"添加成功":"添加失败");
			msg =  status?"添加成功":"添加失败";
			
			break;
		case "update":
			status = dao.update(fee);
			map.put("status", status);
			msg = status?"修改成功":"修改失败";
			break;

		default:
			break;
		}
		
		
		if(status){
			boolean result = applicationMail(ID, toStr, copytoStr, user, pwd);
			msg += result?"，邮件发送成功":"，邮件发送失败";
		}
		map.put("message", msg);
		return map;
	}

	@Override
	public boolean applicationMail(int ID, String toStr, String copytoStr,String sender,String pwd) {
		String visitTime = "NA";
		String CustomerName = "NA";
		String ServiceItem = "NA";
		String Engineer = "NA";
		SaleFeeDao dao = new SaleFeeDao();
		List<Map<String, Object>> list = dao.getInfoByID(ID);
		
		if(list.size()>1){
			visitTime = list.get(1).get("VisitTime").toString();
			CustomerName = list.get(1).get("CustomerName").toString();
			ServiceItem = list.get(1).get("ServiceItem").toString();
			Engineer = list.get(1).get("Engineer").toString();
		}
		String[] to = toStr.split(";");
		String[] copyto = copytoStr.split(";");
		String subject = "Eoulu：拜访申请："+visitTime+CustomerName+ServiceItem+Engineer;
		String receiver = "NA";
		String tel = "NA";
		String senderName = "NA";
		try{
			StaffInfoDao infoDao = new StaffInfoDao();
			List<Map<String, Object>> list2 = infoDao.getTelAndName(to[0]);
			receiver = list2.get(1).get("StaffName").toString();
			list2 = infoDao.getTelAndName(sender);
			tel = list2.get(1).get("LinkTel").toString();
			senderName = list2.get(1).get("StaffName").toString();
		}catch(Exception e){
			return false;
		}

		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>"+receiver.replace(" ", "").substring(receiver.replace(" ", "").length()-2)+"，你好！</span><br><br>");
		sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>请查收以下服务申请：</span><br><br>");
		sBuilder.append("<table border='1' cellpadding='4' cellspacing='0' style='font-family: 微软雅黑;font-size: 14px;'><tbody><tr><td>拜访时间</td><td>客户单位</td><td>服务事宜</td><td>工程师</td></tr><tr><td>"+visitTime+"</td><td>"+CustomerName+"</td><td>"+ServiceItem+"</td><td>"+Engineer+"</td></tr></tbody></table><br>");
		sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>十分感谢。</span><br><br>");
		String content = new MethodUtil().getStaffEmailSign(sBuilder.toString(), senderName, tel, sender);
		
		boolean flag = new JavaMailToolsUtil(sender, sender, pwd).doSendHtmlEmail(subject, content, null, to, copyto);
	
		return flag;
	}

	@Override
	public Map<String, Object> review(int ID,String isPass, String reason,String user) {
		Map<String, Object> map = new HashMap<>();
		SaleFeeDao dao = new SaleFeeDao();
		List<Map<String, Object>> list = dao.getInfoByID(ID);
		String reviewer = list.get(1).get("Recipient").toString();
		if(!user.equals(reviewer)){
			map.put("status", false);
			map.put("message", "抱歉，您无此操作权限");
			return map;
		}
		String receiver = list.get(1).get("SalesMan").toString();
		List<Map<String, Object>> list2 = new StaffInfoDao().getMail(receiver);
		String[] to = null;
		if(list2.size()>1){
			to= new String[]{list2.get(1).get("StaffMail").toString()};
		}
		String subject = "Eoulu：拜访申请审批结果通知";
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>"+receiver.replace(" ", "").substring(receiver.replace(" ", "").length()-2)+"，你好！</span><br><br>");
		switch (isPass) {
		case "已通过":
			sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>你的申请已通过，请悉知。</span><br><br>");
			
			break;
		case "未通过":
			sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>你的申请未通过，原因如下：</span><br><br>");
			
			sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>"+reason+"。</span><br><br>");
			sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>请悉知！</span><br><br>");
		default:
			break;
		}
		String content = new MethodUtil().getEmailSign(sBuilder.toString(), "NA");
		Properties pro = new Properties();
		String umail;
		String uname;
		String pwd;
		InputStream inputStream = null;
	
		try {
			inputStream = SendMailUtil.class.getResourceAsStream("email.properties");
			pro.load(inputStream);
		} catch (IOException e1) {
			e1.printStackTrace();
		}finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		umail = pro.getProperty("SEND_USER");
		uname = pro.getProperty("SEND_UNAME");
		pwd = pro.getProperty("SEND_PWD");
		boolean flag = new JavaMailToolsUtil(umail, uname, pwd).doSendHtmlEmail(subject, content, null, to, null);
		if(flag){
			dao.updateIsPass(isPass, ID);
			if(isPass.equals("已通过")){
				ScheduleDao scheduleDao = new ScheduleDao();
				scheduleDao.insertBySale(list.get(1).get("VisitTime").toString(), list.get(1).get("Engineer").toString(), 
						list.get(1).get("CustomerName").toString(), list.get(1).get("ServiceItem").toString());
				scheduleDao.insertBySale(list.get(1).get("VisitTime").toString(), list.get(1).get("SalesMan").toString(), 
						list.get(1).get("CustomerName").toString(), list.get(1).get("ServiceItem").toString());
			}
		}
		map.put("status",flag);
		map.put("message",flag?"发送成功":"发送失败");
		return map;
	}

	@Override
	public int getAllCount() {
		SaleFeeDao dao = new SaleFeeDao();
		return dao.getAllCount();
	}

}
