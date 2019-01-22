package com.eoulu.syn;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;

import com.eoulu.dao.LoanApplicationDao;
import com.eoulu.entity.LoanApplication;
import com.eoulu.util.Java2Word;
import com.eoulu.util.Word2Pdf;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ExportLoanApplication {
	
	private String CustomerName;
	private String Department; 
	private String Contact; 
	private String ApplicationNo; 
	private String LoanDate; 
	private String ExpectedReturnDate; 
	private String Phone;
	private String goodsJson; 
	private int ID;
	private List<Map<String, Object>> goods;
	
	public void setByRequest(HttpServletRequest request){
		ID = Integer.parseInt(request.getParameter("ID"));
		CustomerName = request.getParameter("CustomerName")==null?"":request.getParameter("CustomerName").trim();
		Department = request.getParameter("Department")==null?"":request.getParameter("Department");
		Contact = request.getParameter("Contact")==null?"":request.getParameter("Contact");
		ApplicationNo = request.getParameter("ApplicationNo")==null?"":request.getParameter("ApplicationNo"); 
		LoanDate = request.getParameter("LoanDate")==null?"":request.getParameter("LoanDate"); 
		ExpectedReturnDate = request.getParameter("ExpectedReturnDate")==null?"":request.getParameter("ExpectedReturnDate");
		goodsJson = request.getParameter("GoodsJson");
		Phone = request.getParameter("Phone");
		request.getParameter("SerialNumber");
		goods = new ArrayList<>();
		if(goodsJson!=null){
			JSONArray array = JSONArray.fromObject(goodsJson);
			for(int i = 0;i < array.size();i++){
				Map<String, Object> updateMap = new HashMap<>();
				JSONObject object = array.getJSONObject(i);
				updateMap.put("Model",(String)object.get("Model"));
				updateMap.put("Description", (String)object.get("Description"));
				updateMap.put("SerialNumber", (String)object.get("SerialNumber"));
				try{
					updateMap.put("Qty", object.get("Qty").toString());
				}
				catch (NumberFormatException e){
			
				}
				goods.add(updateMap);
			}
		}
		LoanApplication application = new LoanApplication();
		LoanApplicationDao dao = new LoanApplicationDao();
		application.setID(ID);
		application.setCustomerName(CustomerName);
		application.setDepartment(Department);
		application.setContact(Contact);
		application.setLoanDate(LoanDate);
		application.setApplicationNo(ApplicationNo);
		application.setExpectedReturnDate(ExpectedReturnDate);
		application.setGoodsJson(goodsJson);
		application.setPhone(Phone);
		dao.updateDocument(application,goods);
		
	}
	
	public void setByDB(int ID){
		LoanApplicationDao dao = new LoanApplicationDao();
		List<Map<String, Object>> application = dao.getApplication(ID);
		CustomerName = application.get(1).get("CustomerName").toString();
		Department = application.get(1).get("Department").toString();
		Contact = application.get(1).get("Contact").toString();
		ApplicationNo = application.get(1).get("ApplicationNo").toString();
		LoanDate = application.get(1).get("LoanDate").toString();
		ExpectedReturnDate = application.get(1).get("ExpectedReturnDate").toString();
		Phone = application.get(1).get("Phone").toString();
		List<Map<String, Object>> goodsList = dao.getGoods(ID);
		goodsList.remove(0);
		goods = goodsList;
	}

	
	public String export(String downLoadUrl){
		
		HashMap<String, Object> data = new HashMap<>();
	
		data = new HashMap<>();
		data.put("${CustomerName}", CustomerName);
		data.put("${Department}", Department);
		data.put("${Contact}", Contact);
		data.put("${ApplicationNo}", ApplicationNo);
		data.put("${LoanDate}", LoanDate);
		data.put("${ExpectedReturnDate}", ExpectedReturnDate);
		data.put("${Phone}", Phone);
		
		
		
		if(goods.size()>0){
			ArrayList table = new ArrayList(goods.size());
			String[] fieldName = { "1", "2", "3", "4", "5"};
			table.add(fieldName);
			for (int i = 0; i < goods.size(); i++) {
				String[] field = { i + 1 + "", goods.get(i).get("Model").toString(), 
						goods.get(i).get("Description").toString(),goods.get(i).get("SerialNumber").toString(),goods.get(i).get("Qty").toString() };
				table.add(field);
			}
			data.put("table$2@2", table);
		}
		
		downLoadUrl = downLoadUrl + ApplicationNo +".doc";
		String newName = ExportQuotePDF.copyFile("Eoulu借用申请单模板.docx");
        if(newName.equals("error")){
        	return newName;
        }else{
        	Java2Word word = new Java2Word();
        	String srcPath = "E:/Model/"+newName;
	        word.toWord(srcPath,downLoadUrl,data,"end");
	        Word2Pdf.toPdf(downLoadUrl, downLoadUrl.replace(".doc", ".pdf"));
	        try {
				FileUtils.forceDelete(new File(srcPath));
			} catch (IOException e) {
				e.printStackTrace();
			}
	        
	        return downLoadUrl.replace(".doc", ".pdf");
        }
	}

	public String getApplicationNo() {
		return ApplicationNo;
	}

	public void setApplicationNo(String applicationNo) {
		ApplicationNo = applicationNo;
	}
	

}
