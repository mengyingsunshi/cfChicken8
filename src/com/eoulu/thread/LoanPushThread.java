package com.eoulu.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.eoulu.dao.LoanApplicationDao;
import com.eoulu.dao.StaffInfoDao;
import com.eoulu.syn.ExportLoanApplication;
import com.eoulu.util.JavaMailToolsUtil;
import com.eoulu.util.MethodUtil;

public class LoanPushThread implements Runnable{ 
	private LoanApplicationDao dao;
	private int loanID;
	private String[] to = null;
	private String[] copyto = null;
	private String downLoadUrl;
	private String user;
	private String uname;
	private String pwd;
	private List<String> error = new ArrayList<>();
	
	
	

	public LoanPushThread(LoanApplicationDao dao, int loanID, String[] copyto, String downLoadUrl,
			String user, String uname, String pwd, List<String> error) {
		super();
		this.dao = dao;
		this.loanID = loanID;
		this.copyto = copyto;
		this.downLoadUrl = downLoadUrl;
		this.user = user;
		this.uname = uname;
		this.pwd = pwd;
		this.error = error;
	}


	@Override
	public void run() {
		List<Map<String, Object>> info = dao.getApplication(loanID);
		String applicationNo = "NA";
		String applicant = "NA";
		String applicationDate = "NA";
		String customerName = "NA";
		String contact = "NA";
		String model = "NA";
		String expectedReturnDate = "NA";
		if(info.size()>1){
			applicationNo = info.get(1).get("ApplicationNo").toString();
			applicant = info.get(1).get("Applicant").toString();
			applicationDate = info.get(1).get("ApplicationDate").toString();
			customerName = info.get(1).get("CustomerName").toString();
			contact = info.get(1).get("Contact").toString();
			expectedReturnDate = info.get(1).get("ExpectedReturnDate").toString();
		}
		List<Map<String, Object>> mail = new StaffInfoDao().getMail(applicant);
		List<Map<String, Object>> goods = dao.getGoods(loanID);
		if(mail.size()>1){
			to= new String[]{mail.get(1).get("StaffMail").toString()};
		}
		if(goods.size()>1){
			model = goods.get(1).get("Model").toString();
		}
		String subject = "Eoulu：借用归还提醒—"+applicationDate+customerName+contact+model+"的借用申请（"+applicationNo+"）" ;
		StringBuilder sBuilder = new StringBuilder();
		sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>"+applicant+",您好！</span><br><br>");
		sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>附件："+applicationNo+".pdf，是"
			+customerName+contact+model+"的借用申请，预计归还日期为"+expectedReturnDate+"，烦请告知客户及时归还，如不能及时归还，"
			+ "请确认客户是否采购，若不能按时归还且不能按时采购，将按照当月的标准售价扣在个人account账户里。</span><br><br>");
			
		sBuilder.append("<span style='font-family:微软雅黑;font-size:14px;'>以上请知悉，非常感谢！</span><br>");
	
		StaffInfoDao dao = new StaffInfoDao();
		List<Map<String, Object>> list = dao.getMail(applicant);
		if(list.size()>1){
			to = new String[]{list.get(1).get("StaffMail").toString()};
		}
		String content = new MethodUtil().getEmailSign(sBuilder.toString(), "NA");
		ExportLoanApplication util = new ExportLoanApplication();
		util.setByDB(loanID);
		String fileLocate = util.export(downLoadUrl); 
		boolean result = new JavaMailToolsUtil(user,uname, pwd).doSendHtmlEmail(subject, content, new String[]{fileLocate}, to, copyto);
		if(!result){
			error.add(util.getApplicationNo());
		}	
	}

}
