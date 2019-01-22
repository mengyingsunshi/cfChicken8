package com.eoulu.syn;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;

import com.eoulu.util.Java2Word;

public class ExportWorkLog {

	HashMap<String, Object> data = new HashMap<>();
	
	public String exportLog(HttpServletRequest request){
		String Name = request.getParameter("Name")==null?"":request.getParameter("Name").trim();
		String Morning = request.getParameter("Morning")==null?"":request.getParameter("Morning");
		String Afternoon = request.getParameter("Afternoon")==null?"":request.getParameter("Afternoon");
		String MorningPlan = request.getParameter("MorningPlan")==null?"":request.getParameter("MorningPlan"); 
		String AfternoonPlan = request.getParameter("AfternoonPlan")==null?"":request.getParameter("AfternoonPlan"); 
		String Introspection = request.getParameter("Introspection")==null?"":request.getParameter("Introspection");
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMdd");
		String date = simpleDateFormat.format(new Date());
		data = new HashMap<>();
		data.put("${Date}", date);
		data.put("${Name}", Name);
		data.put("${Morning}", Morning);
		data.put("${Afternoon}", Afternoon);
		data.put("${MorningPlan}", MorningPlan);
		data.put("${AfternoonPlan}", AfternoonPlan);
		data.put("${Introspection}", Introspection);
		
		String downLoadUrl = request.getServletContext().getRealPath("/") + "down\\工作日志"+date+"-"+Name
				+ ".doc";
		String newName = ExportQuotePDF.copyFile("工作日志模板.doc");
        if(newName.equals("error")){
        	return newName;
        }else{
        	Java2Word word = new Java2Word();
        	String srcPath = "E:/Model/"+newName;
	        word.toWord(srcPath,downLoadUrl,data,"end");
	        try {
				FileUtils.forceDelete(new File(srcPath));
			} catch (IOException e) {
				e.printStackTrace();
			}
	
	        return downLoadUrl;
        }
	}

}
