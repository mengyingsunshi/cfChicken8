/**
 * 
 */
package com.eoulu.service.impl;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.junit.Test;

import com.eoulu.service.StatisticsService;
import com.eoulu.util.DBUtil;


/**
 * @author zhangkai
 *
 */
public class StatisticsServiceImpl implements StatisticsService {

	/* (non-Javadoc)
	 * @see com.eoulu.service.StatisticsService#getStatisticsBySales(java.lang.String, java.lang.String)
	 */
	/**
	 * ��ȡ���۴��������ҵ��������
	 * */
	@Override
	public List<Map<String, Object>> getStatisticsBySales(String startTime, String endTime) {

		String sql = "select t_sales_representative.Name SalesRepresentative,count(B.ID) RNumbers,ROUND(SUM(B.RMBQuotes/6.7+B.USDQuotes)/1000000,2) CompletValue from t_sales_representative left join (select t_order.DateOfSign,t_order.ID,t_order.SalesRepresentative,t_quotes.USDQuotes,t_quotes.RMBQuotes from t_order left join t_quotes on t_order.ID=t_quotes.OrderID where t_order.DateOfSign between ? and ? and t_order.PageType=0)B on t_sales_representative.ID=B.SalesRepresentative "
				+ "where t_sales_representative.Name in ('乐园','刘亚楠','罗晓旭','徐峰','赵娜','张海洋') GROUP BY t_sales_representative.`Name` order by CompletValue desc";
		Object[] parameter = new Object[]{startTime,endTime};
		
		List<Map<String,Object>> ls = new DBUtil().QueryToList(sql, parameter);
		
		int length = ls.size();
		for(int i=1; i<length; i++){
			String comp = ls.get(i).get("CompletValue").toString();
			
			ls.get(i).put("CompletValue", comp.equals("--")?0:comp);
		}
		return ls;
	}
	
	/**
	 * ��ȡ���е����۴���
	 * */
	public List<Map<String, Object>> getAllResp(){
		String sql = "select * from t_sales_representative";
		Object[] parameter = new Object[0];
		
		List<Map<String,Object>> ls = new DBUtil().QueryToList(sql, parameter);
		
		return new DBUtil().QueryToList(sql, parameter);
	}
	
	/**
	 * ��ʱ���ȡ������Ķ�����
	 * */
	@Override
	public List<Map<String, Object>> getStatisticsByArea(String startTime, String endTime) {

		String sql = "select t_area.AreaName,t_area.ID,count(B.ID) RNumbers,ROUND((B.RMBQuotes/6.7+B.USDQuotes)/1000000,2) CompletValue from t_area  left join (select t_order.Area,t_order.DateOfSign,t_order.ID,sum(t_quotes.RMBQuotes) RMBQuotes,sum(t_quotes.USDQuotes) USDQuotes from t_order left join t_quotes on t_order.ID=t_quotes.OrderID where DATE_FORMAT( t_order.DateOfSign,'%Y-%m') between ? and ? and t_order.PageType=0 group by t_order.Area)B on t_area.ID=B.Area  GROUP BY t_area.AreaName order by t_area.ID desc";
		Object[] parameter = new Object[]{startTime,endTime};
		//Object[] parameter = new Object[0];
		List<Map<String,Object>> ls = new DBUtil().QueryToList(sql, parameter);
		List<Map<String,Object>> targetValue = null;

		if(!startTime.split("-")[0].equals("2019")){
			startTime = "2017-"+startTime.split("-")[1];
			endTime = "2017-"+endTime.split("-")[1];
		}
		targetValue = getTargetValueByArea(startTime,endTime);
		
		int length = ls.size();
		ls.get(0).put("colAdd", "TargetValue");
		for(int i=1; i<length; i++){
			ls.get(i).put("TargetValue", targetValue.get(i).get("TargetValue"));
		}
		
		
		//���ݸ�ʽ���������ݵ���0
		for(int i=1; i<length; i++){
			String comp = ls.get(i).get("CompletValue").toString();
			String tar = ls.get(i).get("TargetValue").toString();
			
			ls.get(i).put("CompletValue", comp.equals("--")?0:comp);
			ls.get(i).put("TargetValue", tar.equals("--")?0:tar);
		}
		
	
		return ls;
	}
	/**
	 * ��ȡָ��ʱ����ڵĸ������Ŀ��ֵ
	 * */
	@Override
	public List<Map<String, Object>> getTargetValueByArea(String startTime, String endTime) {

		String sql = "select t_area.AreaName AreaName,ROUND((B.TargetValue)/1000000,2) TargetValue from t_area left join (select sum(t_contract_objective.TargetValue) TargetValue,t_contract_objective.Parameter from t_contract_objective where t_contract_objective.Classify='Area' and  DATE_FORMAT(t_contract_objective.TargetTime,'%Y-%m')  between ? and ? group by t_contract_objective.Parameter)B on t_area.ID=B.Parameter group by AreaName";
		Object[] parameter = new Object[]{startTime,endTime};
		List<Map<String,Object>> ls = new DBUtil().QueryToList(sql, parameter);
		
		return ls;
	}
	
	


	/**
	 * ��ȡָ��ʱ����ڵĸ������Ŀ��ֵ�����ֵ��ƴ�ӳ��ַ�������[[Ŀ��ֵ],[���ֵ]],���ձ���--�Ϸ�--���Ϸ�����
	 * */
	@Override
	public String getAreaDataToString(String startTime, String endTime) {
		List<Map<String,Object>> lsComp = getStatisticsByArea(startTime,endTime);
		
		String[][] areaData = new String[][]{{"",""}};
		
		int length = lsComp.size();
		for(int i=1;i<length;i++){
			areaData[0][0]+=lsComp.get(i).get("TargetValue");
			areaData[0][1]+=lsComp.get(i).get("CompletValue");
			if(i<length-1)
			{
				areaData[0][0]+=",";
			    areaData[0][1]+=",";
			}
		}
		
		
		return "[["+areaData[0][0]+"],["+areaData[0][1]+"]]";
	}
	

	
	/**
	 * ��ȡ���۴����ҵ����������������
	 * */
	@Override
	public String getSalesDataToString(String startTime, String endTime) {
		List<Map<String,Object>> lsSales = getStatisticsBySales(startTime,endTime);
		
		
		String[][] sales = new String[][]{{"",""}};
		int length = lsSales.size();
		
		for(int i=1; i<length; i++){
			sales[0][0]+="'";
			sales[0][0]+=lsSales.get(i).get("SalesRepresentative");
			sales[0][0]+="'";
			sales[0][1]+=lsSales.get(i).get("CompletValue");
			if(i<length-1)
			{
				sales[0][0]+=",";
				sales[0][1]+=",";
			}
		}
		
		
		return "[["+sales[0][0]+"],["+sales[0][1]+"]]";
	}
//	@Test
//	public void test(){
//		System.out.println(getSalesDataToString("2017-01-01","2018-01-01"));
//	}
	
	
	


	/**
	 * ��ȡָ��ʱ���Ŀ��ֵ
	 * */
	@Override
	public float getTargetValue(String startTime, String endTime) {
	
		
		List<Map<String,Object>> ls =  getStatisticsByArea(startTime,endTime);
		//float result = 0;
		BigDecimal result = new BigDecimal("0");
		int length = ls.size();
		for(int i=1; i<length; i++){
			BigDecimal bigDecimal = new BigDecimal(ls.get(i).get("TargetValue").toString());
			result = bigDecimal.add(result);
	
		}
		
		
		return result.floatValue();
	}

	/**
	 * ��ȡָ��ʱ�����ֵ
	 * */
	@Override
	public float getCompleteValue(String startTime, String endTime) {
		
		
		List<Map<String,Object>> ls =  getStatisticsByArea(startTime,endTime);
		
		BigDecimal result = new BigDecimal("0");
		int length = ls.size();
		for(int i=1; i<length; i++){
			BigDecimal bigDecimal = new BigDecimal(ls.get(i).get("CompletValue").toString());
			result = bigDecimal.add(result);
	
		}
	
	
		
		return result.floatValue();
	}
	
	@Override
	public float getCompleteValueByDate(String startTime, String endTime) {
		
		
		String sql = "select count(B.ID) RNumbers,ROUND((B.RMBQuotes/6.7+B.USDQuotes)/1000000,4) CompletValue from (select t_order.Area,t_order.DateOfSign,t_order.ID,sum(t_quotes.RMBQuotes) RMBQuotes,sum(t_quotes.USDQuotes) USDQuotes from t_order left join t_quotes on t_order.ID=t_quotes.OrderID where t_order.DateOfSign>=? and t_order.DateOfSign<=? and t_order.PageType=0)B ";
		Object[] parameter = new Object[]{startTime,endTime};

		List<Map<String,Object>> ls = new DBUtil().QueryToList(sql, parameter);
		
		BigDecimal result = new BigDecimal("0");
		if(ls.size()>1){
			String completValue = ls.get(1).get("CompletValue").toString();
			if(!completValue.equals("--")){
				result = new BigDecimal(completValue);
			}
		}
		
		return result.floatValue();
	}
	public int getContractNum(String startTime,String endTime) {
		
		String sql = "select COUNT(ID) Num from t_order where t_order.DateOfSign>=? and t_order.DateOfSign<=? and t_order.PageType=0";		
		Object[] parameter = new Object[]{startTime,endTime};

		List<Map<String,Object>> ls = new DBUtil().QueryToList(sql, parameter);
		
		int num = 0;
		if(ls.size()>1){
			num = Integer.parseInt(ls.get(1).get("Num").toString());
		}
		return num;
	}

	@Override
	public List<Map<String, Object>> getStatisticsByAreaPerMonth(String year) {
		List<Map<String,Object>> ls = new ArrayList<Map<String,Object>>();
		String targerYear = year;
		if(!targerYear.equals("2019")){
			targerYear = "2017";
		}
		String[] months = new String[]{"01","02","03","04","05","06","07","08","09","10","11","12"};
		for(int i=0 ; i<months.length; i++){
			Map<String,Object> map = new HashMap<>();
			String startTime = year +"-"+months[i];
			String endTime = year +"-"+months[i];
			String targetStart = targerYear+"-"+months[i];
			String targetEnd = targerYear+"-"+months[i];
			map.put("StatisticsByAreaPer"+months[i], getStatisticsByArea(startTime, endTime,targetStart,targetEnd));
		
			ls.add(map);
		}
		
		return ls;
	}

	@Override
	public List<Map<String, Object>> getStatisticsByArea(String startTime, String endTime, String startYear,String endYear) {
		String sql = "select t_area.AreaName,t_area.ID,count(B.ID) RNumbers,ROUND((B.RMBQuotes/6.7+B.USDQuotes)/1000000,2) CompletValue from t_area  left join (select t_order.Area,t_order.DateOfSign,t_order.ID,sum(t_quotes.RMBQuotes) RMBQuotes,sum(t_quotes.USDQuotes) USDQuotes from t_order left join t_quotes on t_order.ID=t_quotes.OrderID where DATE_FORMAT( t_order.DateOfSign,'%Y-%m') between ? and ? AND t_order.PageType=0 group by t_order.Area)B on t_area.ID=B.Area LEFT JOIN (select * from t_contract_objective where t_contract_objective.Classify='Area' and DATE_FORMAT(t_contract_objective.TargetTime,'%Y-%m')  between ? and ?)C on C.Parameter=t_area.ID GROUP BY t_area.AreaName order by t_area.ID desc";
		Object[] parameter = new Object[]{startTime,endTime,startTime,endTime};
		//Object[] parameter = new Object[0];
		List<Map<String,Object>> ls = new DBUtil().QueryToList(sql, parameter);
		
//		String sql2 = "select t_area.AreaName,t_area.ID,count(B.ID) RNumbers,ROUND((B.RMBQuotes/6.7+B.USDQuotes)/1000000,2) CompletValue from t_area  left join (select t_order.Area,t_order.DateOfSign,t_order.ID,sum(t_quotes.RMBQuotes) RMBQuotes,sum(t_quotes.USDQuotes) USDQuotes from t_order left join t_quotes on t_order.ID=t_quotes.OrderID where DATE_FORMAT( t_order.DateOfSign,'%Y-%m') between ? and ? group by t_order.Area)B on t_area.ID=B.Area LEFT JOIN (select * from t_contract_objective where t_contract_objective.Classify='Area' and DATE_FORMAT(t_contract_objective.TargetTime,'%Y-%m')  between ? and ?)C on C.Parameter=t_area.ID GROUP BY t_area.AreaName order by t_area.ID desc";
//		Object[] parameter2 = new Object[]{startYear,endYear,startYear,endYear};
//		List<Map<String,Object>> ls2 = new DBUtil().QueryToList(sql2, parameter2);
		
		List<Map<String,Object>> targetValue = getTargetValueByArea(startYear,endYear);
		int length = ls.size();
		ls.get(0).put("colAdd", "TargetValue");
		for(int i=1; i<length; i++){
			ls.get(i).put("TargetValue", targetValue.get(i).get("TargetValue"));
		}
		
		
		//���ݸ�ʽ���������ݵ���0
		for(int i=1; i<length; i++){
			String comp = ls.get(i).get("CompletValue").toString();
			String tar = ls.get(i).get("TargetValue").toString();
			
			ls.get(i).put("CompletValue", comp.equals("--")?0:comp);
			ls.get(i).put("TargetValue", tar.equals("--")?0:tar);
		}
		//System.out.println(targetValue.size());
		
		return ls;
	}
	public List<Map<String,Object>> getExcelData(String year){
		StatisticsService service = new StatisticsServiceImpl();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		if(year.equals("All")){
			Map<String, Object> map16 = new HashMap<>();
			map16.put("2016", service.getStatisticsByAreaPerMonth("2016"));
			list.add(map16);
			Map<String, Object> map17 = new HashMap<>();
			map17.put("2017", service.getStatisticsByAreaPerMonth("2017"));
			list.add(map17);
			Map<String, Object> map18 = new HashMap<>();
			map18.put("2018", service.getStatisticsByAreaPerMonth("2018"));
			list.add(map18);
		}else{
			Map<String, Object> map = new HashMap<>();
			map.put(year, service.getStatisticsByAreaPerMonth(year));
			list.add(map);
		}
		return list;
	}

	public void exportExcel(List<Map<String,Object>> data,String name,HttpServletResponse response){
		HSSFWorkbook workbook = new HSSFWorkbook();  
//      //设置表格样式
//      HSSFCellStyle style = workbook.createCellStyle();
//      style.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 居中
//      style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
//      //设置字体
//      HSSFFont font = workbook.createFont();  
//      font.setFontName("黑体");  
//      font.setFontHeightInPoints((short) 16);
      
      // 生成一个表格
      HSSFSheet sheet = workbook.createSheet(name+".xls");
      HSSFCellStyle style = workbook.createCellStyle();  
      style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
      style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
      
      for(int i = 0;i < data.size();i ++){
    	  CellRangeAddress callRangeAddressYear = new CellRangeAddress(i*14,i*14+1,0,0);//起始行,结束行,起始列,结束列
    	  CellRangeAddress callRangeAddressArea1 = new CellRangeAddress(i*14,i*14,1,3);
    	  CellRangeAddress callRangeAddressArea2 = new CellRangeAddress(i*14,i*14,4,6);
    	  CellRangeAddress callRangeAddressArea3 = new CellRangeAddress(i*14,i*14,7,9);
    	  sheet.addMergedRegion(callRangeAddressYear);  
          sheet.addMergedRegion(callRangeAddressArea1);  
          sheet.addMergedRegion(callRangeAddressArea2);  
          sheet.addMergedRegion(callRangeAddressArea3);  
          HSSFRow row = sheet.createRow(i*14);  
          HSSFCell cell = row.createCell(0);  
          Iterator<String> iterator = data.get(i).keySet().iterator();
          String year="";
          while (iterator.hasNext()) {
        	  year = iterator.next().toString();
        	  cell.setCellValue(Integer.parseInt(year)); 
        	  cell.setCellStyle(style);
          } 
         
          HSSFCell cell1 = row.createCell(1);
          cell1.setCellValue("北方区域");
          cell1.setCellStyle(style);
          HSSFCell cell2 = row.createCell(4);
          cell2.setCellValue("南方区域");
          cell2.setCellStyle(style);
          HSSFCell cell3 = row.createCell(7);
          cell3.setCellValue("西南区域");
          cell3.setCellStyle(style);
          HSSFRow row1 = sheet.createRow(i*14+1);
          for(int j = 0;j < 3;j++){
        	  HSSFCell cellGoal1 = row1.createCell(j*3+1);
              cellGoal1.setCellValue("月目标(M)");
              HSSFCell cellGoal2 = row1.createCell(j*3+2);
              cellGoal2.setCellValue("月完成(M)");
              HSSFCell cellGoal3 = row1.createCell(j*3+3);
              cellGoal3.setCellValue("月剩余(M)");
          }
          List<Map<String, Object>> yearData = (List<Map<String, Object>>) data.get(i).get(year);
          String[] months = new String[]{"01","02","03","04","05","06","07","08","09","10","11","12"};
          for(int m = 1;m <= yearData.size();m ++){
        	  HSSFRow rowMonData = sheet.createRow(i*14+1+m);
        	  HSSFCell cellMon = rowMonData.createCell(0);
        	  cellMon.setCellValue(m);
        	  List<Map<String, Object>> monData = (List<Map<String, Object>>) yearData.get(m-1).get("StatisticsByAreaPer"+months[m-1]);
        	  HSSFCell cellVal1 = rowMonData.createCell(1);
        	  cellVal1.setCellValue(Double.parseDouble(monData.get(1).get("TargetValue").toString()));
        	  HSSFCell cellVal2 = rowMonData.createCell(2);
        	  cellVal2.setCellValue(Double.parseDouble(monData.get(1).get("CompletValue").toString()));
        	  HSSFCell cellVal3 = rowMonData.createCell(3);
        	  cellVal3.setCellValue(Double.parseDouble(monData.get(1).get("TargetValue").toString())-Double.parseDouble(monData.get(1).get("CompletValue").toString()));
        	  HSSFCell cellVal4 = rowMonData.createCell(4);
        	  cellVal4.setCellValue(Double.parseDouble(monData.get(2).get("TargetValue").toString()));
        	  HSSFCell cellVal5 = rowMonData.createCell(5);
        	  cellVal5.setCellValue(Double.parseDouble(monData.get(2).get("CompletValue").toString()));
        	  HSSFCell cellVal6 = rowMonData.createCell(6);
        	  cellVal6.setCellValue(Double.parseDouble(monData.get(2).get("TargetValue").toString())-Double.parseDouble(monData.get(2).get("CompletValue").toString()));
        	  HSSFCell cellVal7 = rowMonData.createCell(7);
        	  cellVal7.setCellValue(Double.parseDouble(monData.get(3).get("TargetValue").toString()));
        	  HSSFCell cellVal8 = rowMonData.createCell(8);
        	  cellVal8.setCellValue(Double.parseDouble(monData.get(3).get("CompletValue").toString()));
        	  HSSFCell cellVal9 = rowMonData.createCell(9);
        	  cellVal9.setCellValue(Double.parseDouble(monData.get(3).get("TargetValue").toString())-Double.parseDouble(monData.get(3).get("CompletValue").toString()));
			
		}
      }
      BufferedOutputStream fos = null;  
      try {  
          String fileName = name + ".xls";  
          response.setContentType("application/x-msdownload");  
          response.setHeader("Content-Disposition", "attachment;filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ));  
          fos = new BufferedOutputStream(response.getOutputStream());  
          workbook.write(fos);  
      } catch (Exception e) {  
          e.printStackTrace();  
      } finally {  
          if (fos != null) {  
              try {
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}  
          }  
      }  
	}

	@Override
	public List<Map<String, Object>> getSingleQuantity(String startTime, String endTime) {
		List<String> sales = new ArrayList<>();
		sales.add("乐园");
		sales.add("刘亚楠");
		sales.add("罗晓旭");
		sales.add("徐峰");
		sales.add("赵娜");
		sales.add("张海洋");
		List<Map<String, Object>> orderLS;
		List<Map<String, Object>> allRequirementLS;
		List<Map<String, Object>> resultLS = new ArrayList<Map<String, Object>>();
		
		//and t_requirement.RequirementDate BETWEEN ? and ?
		String orderSql = "select B.allCounts,t_sales_representative.`Name` from t_sales_representative LEFT JOIN (select t_requirement.SalesMan,COUNT(t_requirement.ID) allCounts from t_requirement where t_requirement.WhetherSingle=1   GROUP BY t_requirement.SalesMan)B on t_sales_representative.ID=B.SalesMan ";

		String allRequirementSql = "select B.allCounts,t_sales_representative.`Name` from t_sales_representative LEFT JOIN (select t_requirement.SalesMan,COUNT(t_requirement.ID) allCounts from t_requirement GROUP BY t_requirement.SalesMan)B on t_sales_representative.ID=B.SalesMan ";
			
		//Object[] parameter = new Object[] { startTime, endTime };

		DBUtil db = new DBUtil();

		orderLS = db.QueryToList(orderSql, null,false);
		allRequirementLS = db.QueryToList(allRequirementSql,null,false);

		int length = orderLS.size();


		// ͨ����ѯ�����Ķ�Ӧ���۴�����ѳɵ���������е�����������۵ĸ��˳ɵ���
		for (int i = 1; i < length; i++) {
			String name = orderLS.get(i).get("Name").toString();
			if(!sales.contains(name)){
				continue;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			float orderCounts, allCounts;
			map.put("Name", name);

			orderCounts = orderLS.get(i).get("allCounts").equals("--") ? 0
					: Float.parseFloat(orderLS.get(i).get("allCounts").toString());
			allCounts = allRequirementLS.get(i).get("allCounts").equals("--") ? 0
					: Float.parseFloat(allRequirementLS.get(i).get("allCounts").toString());
			float[] count = new float[]{orderCounts,allCounts};
			map.put("Count", count);
			resultLS.add(map);
		}
		int size = resultLS.size();
		int k = 0;
		for(int i = 0;i < size;i ++){
			k = i;
			for(int j = i+1;j < size;j++){
				float[] count_k = (float[]) resultLS.get(k).get("Count");
				float[] count_j = (float[]) resultLS.get(j).get("Count");
			
				if(count_j[1]>count_k[1]){
					k = j;
				}
			}
			Collections.swap(resultLS,i,k);
			
		}
		return resultLS;
			
	}
	
	@Test
	public void test(){
		Calendar calendar = Calendar.getInstance();
		DBUtil dbUtil = new DBUtil();
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
		for(int i = 2016;i < 2020;i ++){
			String M_1 = null,M_2 = null,M_3 = null,M_5 = null,M_7 = null, M_9 = null,M_10=null,M_12=null,M_20=null;
			String start = i + "-01-01";
			try {
				calendar.setTime(sdFormat.parse(start));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		
			String end = "";
			while (!end.equals(i+"-12-31")) {
				calendar.add(Calendar.DATE, 1);
				end = sdFormat.format(calendar.getTime());
				System.out.println("end--"+end);
				float value = getCompleteValueByDate(start, end);
				if(M_1==null){
					if(value >= 1){
						M_1 = end;
						continue;
					}
				}else if(M_2 == null){
					if(value >= 2){
						M_2 = end;
						continue;
					}	
				}else if(M_3 == null){
					if(value >= 3){
						M_3 = end;
						continue;
					}	
				}else if(M_5 == null){
					if(value >= 5){
						M_5 = end;
						continue;
					}	
				}else if(M_7 == null){
					if(value >= 7){
						M_7 = end;
						continue;
					}	
				}else if(M_9 == null){
					if(value >= 9){
						M_9 = end;
						continue;
					}	
				}else if(M_10 == null){
					if(value >= 10){
						M_10 = end;
						continue;
					}	
				}else if(M_12 == null){
					if(value >= 12){
						M_12 = end;
						continue;
					}	
				}else if(M_20 == null){
					if(value >= 20){
						M_20 = end;
						continue;
					}	
				}else{
					break;
				}
				
			}
			String sql = "insert into t_years_sale_stage(Year,1M,2M,3M,5M,7M,9M,10M,12M,20M) values(?,?,?,?,?,?,?,?,?,?)";
			Object[] param = new Object[10];
			param[0] = i;
			param[1] = M_1;
			param[2] = M_2;
			param[3] = M_3;
			param[4] = M_5;
			param[5] = M_7;
			param[6] = M_9;
			param[7] = M_10;
			param[8] = M_12;
			param[9] = M_20;
			
			try {
				System.out.println(i+"---"+dbUtil.executeUpdateNotClose(sql, param));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
			
		}
		dbUtil.closed();
	}
	
	public void updateSaleStage(){
		DBUtil dbUtil = new DBUtil();
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
		int year = calendar.get(Calendar.YEAR);
		String date = sdFormat.format(calendar.getTime());
		float value = getCompleteValueByDate(year + "-01-01",date );
		String sql = "select * from t_years_sale_stage where Year = ?";
		List<Map<String,Object>> list = dbUtil.QueryToList(sql, new Object[]{year},true);
		String M_1="--",M_2="--",M_3="--",M_5="--",M_7="--",M_9="--",M_10="--",M_12="--",M_20="--";
		if(list.size()>1){
			Map<String, Object> stage = list.get(1);
			M_1 = stage.get("1M").toString();
			M_2 = stage.get("2M").toString();
			M_3 = stage.get("3M").toString();
			M_5 = stage.get("5M").toString();
			M_7 = stage.get("7M").toString();
			M_9 = stage.get("9M").toString();
			M_10=stage.get("10M").toString();
			M_12=stage.get("12M").toString();
			M_20=stage.get("20M").toString();
		}
		
		
		if(M_1.equals("--")){
			if(value >= 1){
				M_1 = date;
			}
		}else if(M_2.equals("--")){
			if(value >= 2){
				M_2 = date;
			}	
		}else if(M_3.equals("--")){
			if(value >= 3){
				M_3 = date;
			}	
		}else if(M_5.equals("--")){
			if(value >= 5){
				M_5 = date;
			}	
		}else if(M_7.equals("--")){
			if(value >= 7){
				M_7 = date;
			}	
		}else if(M_9.equals("--")){
			if(value >= 9){
				M_9 = date;
			}	
		}else if(M_10.equals("--")){
			if(value >= 10){
				M_10 = date;
			}	
		}else if(M_12.equals("--")){
			if(value >= 12){
				M_12 = date;
			}	
		}else if(M_20.equals("--")){
			if(value >= 20){
				M_20 = date;
			}	
		}
		String sql2 = "";
		Object[] param = null;
		if(list.size()>1){
			sql2 = "update t_years_sale_stage set 1M = ?,2M = ?,3M = ?,5M = ?,7M = ?,9M = ?,10M = ?,12M = ?,20M = ? where Year = ?";
			param = new Object[10];
			param[0] = M_1;
			param[1] = M_2;
			param[2] = M_3;
			param[3] = M_5;
			param[4] = M_7;
			param[5] = M_9;
			param[6] = M_10;
			param[7] = M_12;
			param[8] = M_20;
			param[9] = year;
		}else{
			sql2 = "insert into t_years_sale_stage(Year,1M,2M,3M,5M,7M,9M,10M,12M,20M) values(?,?,?,?,?,?,?,?,?,?)";
			param = new Object[10];
			param[0] = year;
			param[1] = M_1;
			param[2] = M_2;
			param[3] = M_3;
			param[4] = M_5;
			param[5] = M_7;
			param[6] = M_9;
			param[7] = M_10;
			param[8] = M_12;
			param[9] = M_20;
		}
		dbUtil.executeUpdate(sql2, param); 
		
		
	}
	

	@Override
	public Map<String, Object> getSalesStage(String date) {
		DBUtil dbUtil = new DBUtil();
		String sql = "select Year,1M,2M,3M,5M,7M,9M,10M,12M,20M from t_years_sale_stage";
		List<Map<String, Object>> list = dbUtil.QueryToList(sql, null, false);
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
		if(date == null){
			date = sdFormat.format(new Date());
			date = date.substring(date.indexOf("-")+1);
		}
		int thisYear = calendar.get(Calendar.YEAR);
		Map<String, Object> yearMap = new HashMap<>();
		for(int year = 2016;year <= thisYear;year ++){
			String Start = year + "-"+"01-01";
			String end = year + "-"+date;
			Map<String, Object> map = new HashMap<>();
			float value = getCompleteValueByDate(Start, end);
			int num = getContractNum(Start, end);
			map.put("sales", String.valueOf(value));
			map.put("contract", num);
			Map<String, Object> stage = null;
			for(int i = 1;i < list.size();i ++){
				stage = list.get(i);
				if(stage.get("Year").equals(year+"")){
					map.put("stage", stage);
				}
				
			}
			yearMap.put(year+"",map);
				
		}
		return yearMap;
	}
	
}
