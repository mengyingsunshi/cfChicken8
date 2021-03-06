package com.eoulu.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eoulu.commonality.Page;

public interface SoftwareProjectService {
	public List<Map<String, Object>> getAllData(Page page,String queryJson,String column,String order);
	public int getAllCounts(String queryJson);
	public String insert(HttpServletRequest request);
	public String update(HttpServletRequest request);
	public List<Map<String, Object>> getSoftwareStaff();
	public void exportExcel(HttpServletResponse resp);
	public Map<String, String> upload(HttpServletRequest request);
	public String getPath(HttpServletRequest request);
	public String delete(int id);
	

}
