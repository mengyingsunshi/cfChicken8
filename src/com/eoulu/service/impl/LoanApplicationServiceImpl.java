package com.eoulu.service.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import com.eoulu.commonality.Page;
import com.eoulu.dao.LoanApplicationDao;
import com.eoulu.dao.StaffInfoDao;
import com.eoulu.entity.LoanApplication;
import com.eoulu.service.LoanApplicationService;
import com.eoulu.syn.ExportLoanApplication;
import com.eoulu.thread.LoanPushThread;
import com.eoulu.util.DBUtil;
import com.eoulu.util.JavaMailToolsUtil;
import com.eoulu.util.MethodUtil;
import com.eoulu.util.SendMailUtil;
import com.google.gson.Gson;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class LoanApplicationServiceImpl implements LoanApplicationService{
	
	private static Map<String, String> map;
	static{
		map = new HashMap<>();
		map.put("区域", "Area");
		map.put("客户名称", "CustomerName");
		map.put("借用申请编号", "ApplicationNo");
		map.put("申请日期", "ApplicationDate");
		map.put("货物名称", "Model");
		map.put("是否归还", "IsReturn");
	}

	@Override
	public Map<String, Object> operateApplication(LoanApplication application,String type) {
		Map<String, Object> map = new HashMap<>();
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date2 = null;
		Date date1 = null;
		try {
			date2 = sdFormat.parse(application.getExpectedReturnDate());
			date1 = sdFormat.parse(application.getLoanDate());
		} catch (ParseException e) {
			e.printStackTrace();
			map.put("message", "请检查日期填写是否完整");
			map.put("status", false);
			return map;
		}

		if(MethodUtil.differentDays(date1, date2)>60){
			map.put("message", "借用时间不允许大于60天");
			map.put("status", false);
			return map;
		}
		if(application.getCustomerName().equals("")||application.getContact().equals("")||application.getPhone().equals("")){
			map.put("message", "客户信息未填写完整");
			map.put("status", false);
			return map;
		}
		String goodsJson = application.getGoodsJson();
		System.out.println(goodsJson);
		JSONArray array = JSONArray.fromObject(goodsJson);
		List<Map<String, Object>> list = new ArrayList<>();
		for(int i = 0;i < array.size();i++){
			Map<String, Object> updateMap = new HashMap<>();
			JSONObject object = array.getJSONObject(i);
			updateMap.put("Model",(String)object.get("Model"));
			updateMap.put("Description", (String)object.get("Description"));
			updateMap.put("SerialNumber", object.get("SerialNumber"));
			try{
				updateMap.put("Qty", Integer.parseInt(object.get("Qty").toString()));
			}
			catch (NumberFormatException e){
				map.put("message", "货物数量必须为纯数字格式");
				map.put("status", false);
				return map;
			}
			list.add(updateMap);
		}
		LoanApplicationDao dao = new LoanApplicationDao();
		boolean flag = false;  
		//防止同时提交申请编号重复
		if(type.equals("add")){
			synchronized (LoanApplicationDao.class) {
				String applicationNo = getApplicationNo();
				application.setApplicationNo(applicationNo);
				DBUtil dbUtil = new DBUtil();
				Connection conn = dbUtil.getConnection();
				try {
					conn.setAutoCommit(false);
					int ID = dao.insert(application, dbUtil);
					dao.addGoods(list, ID, dbUtil);
					conn.commit();
					flag = true;
				} catch (Exception e) {
					e.printStackTrace();
					try {
						conn.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}finally {
					dbUtil.closed();
				}
			}
			if(flag){
				map.put("message", "添加成功");
			}else{
				map.put("message", "添加失败");
			}
		}else {
			DBUtil dbUtil = new DBUtil();
			Connection conn = dbUtil.getConnection();
			System.out.println(application);
			try {
				conn.setAutoCommit(false);
				dao.update(application, dbUtil);
				dao.deleteGoods(application.getID(), dbUtil);
				dao.addGoods(list,application.getID(), dbUtil);
				conn.commit();
				flag = true;
			} catch (Exception e) {
				e.printStackTrace();
				try {
					conn.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}finally {
				dbUtil.closed();
			}
			if(flag){
				map.put("message", "修改成功");
			}else{
				map.put("message", "修改失败");
			}
		}
		
		map.put("status", flag);
		return map;
	}



	@Override
	public String getApplicationNo() {
		LoanApplicationDao dao = new LoanApplicationDao();
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyyMMdd");
		String today = sdFormat.format(new Date());
		List<Map<String, Object>> list = dao.getTodayCount(today);
		int lastNo = 0;
		if(list.size()>1){
			lastNo = Integer.parseInt(list.get(1).get("ApplicationNo").toString().split("-")[1]);
		}
	
		String applicationNo = "LOAN"+today+"-"+(lastNo+1);
		return applicationNo;
	}

	@Override
	public List<Map<String, Object>> getDataByPage(Page page, String column1, String content1, String column2,
			String content2,boolean filter) {
		LoanApplicationDao dao = new LoanApplicationDao();
		String sql = "select * from t_loan_application ";
		Object[] param = new Object[]{(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
		if(column1!=null&&column2==null){
			String columnName = map.get(column1);
			if(columnName.equals("ApplicationDate")){
				sql += "where ApplicationDate >= ? and ApplicationDate <= ?";
				param = new Object[]{content1.split("--")[0],content1.split("--")[1],(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
			}else if (columnName.equals("Model")) {
				sql += "where ID IN(select LoanID from t_loan_model where Model like ?)";
				param = new Object[]{"%"+content1+"%",(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
				
			}
			else{
				sql+= "where "+columnName+" like ?";
				param = new Object[]{"%"+content1+"%",(page.getCurrentPage()-1)*page.getRows(),page.getRows()};
			}
		}
		if(column1!=null&&column2!=null){
			String columnName1 = map.get(column1);
			String columnName2 = map.get(column2);
			Object[] param1 = null;
			Object[] param2 = null;
			if(columnName1.equals("ApplicationDate")){
				sql += "where ApplicationDate >= ? and ApplicationDate <= ?";
				param1 = new Object[]{content1.split("--")[0],content1.split("--")[1]};
			}else if (columnName1.equals("Model")) {
				sql += "where ID IN(select LoanID from t_loan_model where Model like ?)";
				param1 = new Object[]{"%"+content1+"%"};
				  
			}
			else{
				sql+= "where "+columnName1+" like ?";
				param1 = new Object[]{"%"+content1+"%"};
			}
			if(columnName2.equals("ApplicationDate")){
				sql += " and ApplicationDate >= ? and ApplicationDate <= ?";
				param2 = new Object[]{content2.split("--")[0],content2.split("--")[1]};
			}else if (columnName2.equals("Model")) {
				sql += "where ID IN(select LoanID from t_loan_model where Model like ?)";
				param2 = new Object[]{"%"+content1+"%"};
				
			}
			else{
				sql+= " and "+columnName2+" like ?";
				param2 = new Object[]{"%"+content2+"%"};
			}
			
			param = new Object[param1.length+param2.length+2];
			for(int i = 0;i < param1.length;i++){
				param[i] = param1[i];
			}
			for(int i = 0;i < param2.length;i++){
				param[i+param1.length] = param2[i];
			}
			param[param.length-2] = (page.getCurrentPage()-1)*page.getRows();
			param[param.length-1] = page.getRows();
		}
		sql += " order by OperatingTime desc limit ?,?";
		List<Map<String, Object>> list = dao.getDataByPage(sql, param);
		for(int i = 1;i< list.size();i++){
			int ID = Integer.parseInt(list.get(i).get("ID").toString());
			List<Map<String, Object>> goods = dao.getGoods(ID);
			if(goods.size()>1){
				list.get(i).put("ItemName",goods.get(1).get("Model"));
			}else{
				list.get(i).put("ItemName","");
			}
		}
		
		if(filter){
			Iterator<Map<String,Object>> iterator = list.listIterator(1);
			while (iterator.hasNext()) {
				Map<String,Object> map = iterator.next();
				
				if(!map.get("IsPass").equals("已通过")&&!map.get("IsReturn").equals("否")){
					iterator.remove();
				}
			}
		}
		return list;
		
	}

	@Override
	public int getCount(String column1, String content1, String column2, String content2) {
		LoanApplicationDao dao = new LoanApplicationDao();
		String sql = "select COUNT(ID) Count from t_loan_application ";
		Object[] param = null;
		if(column1!=null&&column2==null){
			String columnName = map.get(column1);
			if(columnName.equals("ApplicationDate")){
				sql += "where ApplicationDate >= ? and ApplicationDate <= ?";
				param = new Object[]{content1.split("--")[0],content1.split("--")[1]};
			}else if (columnName.equals("Model")) {
				sql += "where ID IN(select LoanID from t_loan_model where Model like ?)";
				param = new Object[]{"%"+content1+"%"};
				
			}
			else{
				sql+= "where "+columnName+" like ?";
				param = new Object[]{"%"+content1+"%"};
			}
		}
		if(column1!=null&&column2!=null){
			String columnName1 = map.get(column1);
			String columnName2 = map.get(column2);
			Object[] param1 = null;
			Object[] param2 = null;
			if(columnName1.equals("ApplicationDate")){
				sql += "where ApplicationDate >= ? and ApplicationDate <= ?";
				param1 = new Object[]{content1.split("--")[0],content1.split("--")[1]};
			}else if (columnName1.equals("Model")) {
				sql += "where ID IN(select LoanID from t_loan_model where Model like ?)";
				param1 = new Object[]{"%"+content1+"%"};
				
			}
			else{
				sql+= "where "+columnName1+" like ?";
				param1 = new Object[]{"%"+content1+"%"};
			}
			if(columnName2.equals("ApplicationDate")){
				sql += " and ApplicationDate >= ? and ApplicationDate <= ?";
				System.out.println(content2);
				param2 = new Object[]{content2.split("--")[0],content2.split("--")[1]};
			}else if (columnName2.equals("Model")) {
				sql += "where ID IN(select LoanID from t_loan_model where Model like ?)";
				param2 = new Object[]{"%"+content1+"%"};
				
			}
			else{
				sql+= " and "+columnName2+" like ?";
				param2 = new Object[]{"%"+content2+"%"};
			}
			
			param = new Object[param1.length+param2.length];
			for(int i = 0;i < param1.length;i++){
				param[i] = param1[i];
			}
			for(int i = 0;i < param2.length;i++){
				param[i+param1.length] = param2[i];
			}
	
		}
		return dao.getCount(sql, param);
		
	}
	
	@Test
	public void test(){
		Map<String, Object> map = new HashMap<>();
		map.put("Model", "summit");
		map.put("Description", "dddddddddddddddd");
		map.put("Qty", 2);
		System.out.println(new Gson().toJson(map));
		
	}



	@Override
	public boolean sendApplicationMail(HttpServletRequest request,String catelog) {
		String sender = "";
		String pwd = "";
		String[] to = null;
		String copyList = request.getParameter("copyList");
		String content = request.getParameter("content");
		String subject = request.getParameter("subject");
		pwd = request.getParameter("pwd");
		
		String[] copyto = copyList.split(";");
		MethodUtil methodUtil = new MethodUtil();
		ExportLoanApplication util = new ExportLoanApplication();
		
		StringBuilder sBuilder = new StringBuilder();
		System.out.println(sBuilder);
		String[] contentArr = content.split("<br>");
		for(int i = 0;i < contentArr.length;i ++){
			sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>"+contentArr[i]
	        + "</span><br><br>");
		}
		String downLoadUrl = request.getServletContext().getRealPath("/") + "down\\";
		if(catelog.equals("push")){
			Properties pro = new Properties();
			InputStream in = SendMailUtil.class.getResourceAsStream("email.properties");
		
			try {
				pro.load(in);
				sender = pro.getProperty("SEND_USER");
				pwd = pro.getProperty("SEND_PWD");
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			String applicant = request.getParameter("Applicant");
			int ID = Integer.parseInt(request.getParameter("ID"));
			StaffInfoDao dao = new StaffInfoDao();
			List<Map<String, Object>> list = dao.getMail(applicant);
			if(list.size()>1){
				to = new String[]{list.get(1).get("StaffMail").toString()};
			}else {
				to = new String[]{};
			}
			content = methodUtil.getEmailSign(sBuilder.toString(), "NA");
			util.setByDB(ID);

			
		}else {
			sender = request.getSession().getAttribute("email").toString(); 
			String toList = request.getParameter("toList");
			to = toList.split(";");
			StaffInfoDao dao = new StaffInfoDao();
			String name = "NA";
			String tel = "NA";
			List<Map<String, Object>> list = dao.getTelAndName(sender);
			if(list.size()>1){
				name = list.get(1).get("StaffName").toString();
				tel = list.get(1).get("LinkTel").toString();
			}
			content = methodUtil.getStaffEmailSign(sBuilder.toString(), name, tel, sender);
			util.setByRequest(request);
		}
		String fileLocate = util.export(downLoadUrl); 
		return new JavaMailToolsUtil(sender, sender, pwd).doSendHtmlEmail(subject, content, new String[]{fileLocate}, to, copyto);
	}






	@Override
	public boolean updateIsPass(int ID,String isPass,String reason) {
		String[] to = null;
		String[] copyto = null;
		Properties pro = new Properties();
		Properties pro2 = new Properties();
		List<String> ls = new ArrayList<>();
		String user;
		String uname;
		String pwd;
	
		try {
			pro.load(SendMailUtil.class.getResourceAsStream("email.properties"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		user = pro.getProperty("SEND_USER");
		uname = pro.getProperty("SEND_UNAME");
		pwd = pro.getProperty("SEND_PWD");
		
		try {
			pro2.load(SendMailUtil.class.getResourceAsStream("loanApplicationReview.properties"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		int copytoCount = Integer.parseInt(pro2.getProperty("copyto"));
		copyto = new String[copytoCount];
		for(int i=0 ; i<copytoCount ; i++){
			int temp = i+1;
			String key = "CopyTo"+temp;
			ls.add(pro2.getProperty(key));
			
		}
		for(int i=0 ; i<ls.size();i++){
			copyto[i] = ls.get(i);
		}
		LoanApplicationDao dao = new LoanApplicationDao();
		List<Map<String, Object>> info = dao.getApplication(ID);
		String applicationNo = "NA";
		String applicant = "NA";
		String applicationDate = "NA";
		if(info.size()>1){
			applicationNo = info.get(1).get("ApplicationNo").toString();
			applicant = info.get(1).get("Applicant").toString();
			applicationDate = info.get(1).get("ApplicationDate").toString();
		}
		List<Map<String, Object>> mail = new StaffInfoDao().getMail(applicant);
		if(mail.size()>1){
			to= new String[]{mail.get(1).get("StaffMail").toString()};
		}
		String subject = "Eoulu：借用申请审批结果通知"+ applicationNo;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>"+applicant+"，您好！</span><br><br>");
		if(isPass.equals("已通过")){
			sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>您于"+applicationDate+"提交的借用申请已经审批通过，请知晓。</span><br><br>");
			sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>此邮件为系统自动发出，无需回复。</span><br>");
		}else if(isPass.equals("未通过")){
			sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>您于"+applicationDate+"提交的借用申请未通过，不通过的原因为："+reason+"，请知悉，谢谢！</span><br>");
		}
		String content = new MethodUtil().getEmailSign(sBuilder.toString(),"NA");
		boolean result = new JavaMailToolsUtil(user, uname, pwd).doSendHtmlEmail(subject, content, null, to, copyto);
		if(result){
			result = dao.updateIsPass(ID,isPass);
		}
		return result;
	}



	@Override
	public List<Map<String, Object>> getGoods(int loanID) {
		LoanApplicationDao dao = new LoanApplicationDao();
		return dao.getGoods(loanID);
	}



	@Override
	public String exportExcel(String path) {
		XSSFWorkbook xwk = new XSSFWorkbook();
		XSSFSheet xsheet = xwk.createSheet("借用申请");
		String[] colName = new String[]{
	       "序号","区域","客户名称","联系人","联系电话","借用申请编号","型号","描述","数量","申请人","申请日期","申请是否通过",
	       "借用日期","预计归还日期","是否归还","实际归还日期","备注"};
		Map<String, Object> colMap = new HashMap<>();
		colMap.put("区域", "Area");
		colMap.put("客户名称", "CustomerName");
		colMap.put("联系人", "Contact");
		colMap.put("联系电话", "Phone");
		colMap.put("借用申请编号", "ApplicationNo");
		colMap.put("型号", "Model");
		colMap.put("描述", "Description");
		colMap.put("数量", "Qty");
		colMap.put("申请人", "Applicant");
		colMap.put("申请日期", "ApplicationDate");
		colMap.put("申请是否通过", "IsPass");
		colMap.put("借用日期", "LoanDate");
		colMap.put("预计归还日期", "ExpectedReturnDate");
		colMap.put("是否归还", "IsReturn");
		colMap.put("实际归还日期", "ActualReturnDate");
		colMap.put("备注", "Remarks");
		List<Map<String, Object>> ls = new LoanApplicationDao().getExcelData();
		
		int m = 0;
		int n = 0;
		boolean continuity = false;
		Map<Integer, Integer> index = new HashMap<>();
		String ID = ls.get(1).get("ID").toString();
		
		for(int i = 2;i < ls.size();i ++){
			String current = ls.get(i).get("ID").toString();
		
			if(current.equals(ID)){
				if(continuity){
					n = n + 1;
				}else{
					m = i - 1;
					n = i;
					if(i == ls.size()-1){
						index.put(m, n);
						System.out.println("------"+m+" "+n);
					}
				}
				continuity = true;
				
			}else{
				if(continuity){
					index.put(m, n);
					System.out.println("------"+m+" "+n);
				}
				continuity = false;
		
			}
			ID = current;
		}
		
		xsheet.setColumnWidth(0, (int) 2000);
		xsheet.setColumnWidth(1, (int) 2000);
		xsheet.setColumnWidth(2, (int) 4000);
		xsheet.setColumnWidth(3, (int) 3000);
		xsheet.setColumnWidth(4, (int) 3000);
		xsheet.setColumnWidth(5, (int) 5000);
		xsheet.setColumnWidth(6, (int) 3000);
		xsheet.setColumnWidth(7, (int) 6000);
		xsheet.setColumnWidth(8, (int) 2000);
		xsheet.setColumnWidth(9, (int) 3000);
		xsheet.setColumnWidth(10, (int) 3000);
		xsheet.setColumnWidth(11, (int) 3000);
		xsheet.setColumnWidth(12, (int) 3000);
		xsheet.setColumnWidth(13, (int) 3000);
		xsheet.setColumnWidth(14, (int) 3000);
		xsheet.setColumnWidth(15, (int) 3000);
		xsheet.setColumnWidth(16, (int) 4000);
		
		for(int start : index.keySet()){
			int end = index.get(start);
			for(int col = 0;col<6;col++){
				CellRangeAddress region = new CellRangeAddress(start, end, col, col);
				xsheet.addMergedRegion(region);
			}
		}
		
		for(int start : index.keySet()){
			int end = index.get(start);
			for(int col = 9;col<colName.length;col++){
				CellRangeAddress region = new CellRangeAddress(start, end, col, col);
				xsheet.addMergedRegion(region);
			}
		}
		
		XSSFCellStyle center = xwk.createCellStyle();
		center.setWrapText(true);// 自动换行
		center.setAlignment(XSSFCellStyle.ALIGN_CENTER);
		center.setVerticalAlignment(XSSFCellStyle.ALIGN_CENTER);
		center.setBorderBottom(CellStyle.BORDER_THIN);
		center.setBorderLeft(CellStyle.BORDER_THIN);
		center.setBorderRight(CellStyle.BORDER_THIN);
		center.setBorderTop(CellStyle.BORDER_THIN);
		int colsCount = colName.length; 
		for(int i=0;i<ls.size();i++){
			XSSFRow xrow = xsheet.createRow(i);
			for(int j=0;j<colsCount; j++){
				XSSFCell xcell = xrow.createCell(j);
				xcell.setCellStyle(center);
				if(i==0){
					xcell.setCellValue(colName[j]);
				}else{
					Map<String,Object> map = ls.get(i);
					switch (colName[j]) {
					case "序号":
						xcell.setCellValue(i+"");
						break;
					case "数量":
						xcell.setCellValue(Integer.parseInt(map.get("Qty").toString()));
						break;
					default:
						xcell.setCellValue(map.get(colMap.get(colName[j])).toString());
						break;
					}
				}
			}
		}
		
		FileOutputStream fo;
		try {
			fo = new FileOutputStream(path);
			xwk.write(fo);
			fo.flush();
			fo.close();
			xwk.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
					
				
		return "down/借用申请.xlsx";
	}



	@Override
	public String sendPushMail(String ids,String downLoadUrl) {
		String[] copyto = null;
		Properties pro = new Properties();
		Properties pro2 = new Properties();
		List<String> ls = new ArrayList<>();
		String user = "";
		String pwd = "";
		
		InputStream in = null;
		in = SendMailUtil.class.getResourceAsStream("LoanApplicationPush.properties");

		try {
			pro2.load(in);
		} catch (IOException e) {
			
			e.printStackTrace();
		}finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		int copytoCount = Integer.parseInt(pro2.getProperty("copyto"));
		copyto = new String[copytoCount];
		for(int i=0 ; i<copytoCount ; i++){
			int temp = i+1;
			String key = "CopyTo"+temp;
			ls.add(pro2.getProperty(key));
			
		}
		for(int i=0 ; i<ls.size();i++){
			copyto[i] = ls.get(i);
		}
	
		
		in = SendMailUtil.class.getResourceAsStream("email.properties");

		try {
			pro.load(in);
			user = pro.getProperty("SEND_USER");
			pwd = pro.getProperty("SEND_PWD");
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		LoanApplicationDao dao = new LoanApplicationDao();
		
		String[] idStrings = ids.split(",");
		List<String> error = new ArrayList<>();
		for(int i = 0;i < idStrings.length;i ++){
			int id = Integer.parseInt(idStrings[i]);
			
			LoanPushThread pushThread = new LoanPushThread(dao,id,copyto,downLoadUrl,user,user,pwd,error);
			Thread thread = new Thread(pushThread);
			thread.start();
		}
		return "邮件请求已发出，请等待片刻查收邮件";

		
	
	}

}
