package com.eoulu.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.eoulu.commonality.Page;

public interface PromotionDataService {

	public List<Map<String, Object>> getDate(Page page);
	
	public int getCounts();
	
	public String getPath(String date,String sheet);
	/**
	 * 处理上传的文件
	 * @param request
	 * @return
	 */
	public boolean uploadExcel(HttpServletRequest request);
	
	public boolean excelToPdf(String excelName, InputStream in)throws IOException;

}
