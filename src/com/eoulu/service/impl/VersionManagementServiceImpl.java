package com.eoulu.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.eoulu.dao.MailConfigDao;
import com.eoulu.dao.StaffInfoDao;
import com.eoulu.dao.VersionManagementDao;
import com.eoulu.entity.VersionManagement;
import com.eoulu.service.VersionManagementService;
import com.eoulu.util.JavaMailToolsUtil;
import com.eoulu.util.MethodUtil;

public class VersionManagementServiceImpl implements VersionManagementService{

	@Override
	public List<Map<String, Object>> getData(String ProjectName) {
		VersionManagementDao dao = new VersionManagementDao();
		return dao.getData(ProjectName);
	}

	@Override
	public Map<String, String> addVersion(VersionManagement version) {
		Map<String, String> map = new HashMap<>();
		Map<String, String> response = new HashMap<>();
 		map.put("futureC_T1","futureC T1");
		map.put("futureD_T1", "futureD T1");
		map.put("futureD_T2","futureD T2");
		map.put("cfChicken_T1", "futureE T1");
		map.put("cfChicken_T2", "futureE T2");
		map.put("EUCP_T1", "EUCP T1");
		

		VersionManagementDao dao = new VersionManagementDao();
		MailConfigDao dao2 = new MailConfigDao();
		StaffInfoDao dao3 = new StaffInfoDao();
		
		boolean mail = false;
	
		String tel = "NA";
		String  name = "NA";
			
		List<Map<String, Object>> info = dao3.getTelAndName(version.getFrom());
		if(info.size()>1){
			tel = info.get(1).get("LinkTel").toString();
			name = info.get(1).get("StaffName").toString();
		}
	
		String projectName = version.getProjectName();
		String vmClassifyHref = version.getVmClassifyHref();
		String toList = "";
		String copyList = "";
		List<Map<String, Object>> mailInfo = dao2.getConfig(projectName);
		if(mailInfo.size()>1){
			toList = mailInfo.get(1).get("ToList").toString();
			copyList = mailInfo.get(1).get("CopyList").toString();
		}
		
		String[] to = toList.split(";");
		String[] copyto = copyList.split(";");
		String subject = "Eoulu："+map.get(projectName)+"-产品上线内容";
		String updatedContent = version.getUpdatedContent();
		StringBuilder builder = new StringBuilder();
		builder.append("<span style='font-family:微软雅黑;font-size:14px;'>您好！</span><br><br>");
		builder.append("<span style='font-family:微软雅黑;font-size:14px;'>以下为<a href='" + vmClassifyHref 
				+ "' style='color: red;' title='冲锋鹅T2-软件部文档页面-"+map.get(projectName)+"'>"+map.get(projectName)
				+ " " + version.getVersion()+"</a>的上线内容：</span><br><br>");
		String[] contentArr = updatedContent.split("VM@splitor");
		for(int i = 0;i < contentArr.length;i ++){
			builder.append("<span style='font-family:微软雅黑;font-size:14px;'>" + (i+1) +"、"+contentArr[i]
	        + "</span><br><br>");
		}
		
		builder.append("<span style='font-family:微软雅黑;font-size:14px;'>请知悉，谢谢！</span><br><br>");
		MethodUtil util = new MethodUtil();
		String content = util.getStaffEmailSign(builder.toString(),name,tel,version.getFrom());
		mail = new JavaMailToolsUtil(version.getFrom(), version.getFrom(), version.getPassword()).doSendHtmlEmail(subject, content, null, to, copyto);
		
		if(mail){
			boolean result = dao.addVersion(version);
			if(result){
				response.put("status", "ok");
				response.put("message", "发布成功");
			}else{
				response.put("status", "fail");
				response.put("message", "邮件发送成功，存表失败");
			}
		}else{
			response.put("status", "fail");
			response.put("message", "邮件发送失败，请再次发送！");
		}
		
		return response;
					
	}

	@Override
	public String getDownloadUrl(String fileName) {
		List<Map<String, Object>> list = new VersionManagementDao().getPath(fileName);
		String path = list.get(1).get("FilePath").toString();
		path = "/LogisticsFile/File/"+path;
		return path;
	}
	
}
