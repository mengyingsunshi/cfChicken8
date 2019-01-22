package com.eoulu.entity;

public class LoanApplication {
	
	private int ID;
	private String ApplicationDate;
	private String Applicant;
	private String ApplicationNo;
	private String IsReturn;
	private String Area;
	private String LoanDate;
	private String ExpectedReturnDate;
	private String ActualReturnDate;
	private String CustomerName;
	private String Department;
	private String Contact;
	private String Phone;
	private String Remarks;
	private String GoodsJson;
	private String LoanAgreement;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getApplicationDate() {
		return ApplicationDate;
	}
	public void setApplicationDate(String applicationDate) {
		ApplicationDate = applicationDate;
	}
	public String getApplicant() {
		return Applicant;
	}
	public void setApplicant(String applicant) {
		Applicant = applicant;
	}
	public String getApplicationNo() {
		return ApplicationNo;
	}
	public void setApplicationNo(String applicationNo) {
		ApplicationNo = applicationNo;
	}
	public String getIsReturn() {
		return IsReturn;
	}
	public void setIsReturn(String isReturn) {
		IsReturn = isReturn;
	}
	public String getArea() {
		return Area;
	}
	public void setArea(String area) {
		Area = area;
	}
	
	public String getLoanDate() {
		return LoanDate;
	}
	public void setLoanDate(String loanDate) {
		LoanDate = loanDate;
	}
	public String getGoodsJson() {
		return GoodsJson;
	}
	public void setGoodsJson(String goodsJson) {
		GoodsJson = goodsJson;
	}
	public String getExpectedReturnDate() {
		return ExpectedReturnDate;
	}
	public void setExpectedReturnDate(String expectedReturnDate) {
		ExpectedReturnDate = expectedReturnDate;
	}
	public String getActualReturnDate() {
		return ActualReturnDate;
	}
	public void setActualReturnDate(String actualReturnDate) {
		ActualReturnDate = actualReturnDate;
	}
	public String getCustomerName() {
		return CustomerName;
	}
	public void setCustomerName(String customerName) {
		CustomerName = customerName;
	}
	public String getDepartment() {
		return Department;
	}
	public void setDepartment(String department) {
		Department = department;
	}
	public String getContact() {
		return Contact;
	}
	public void setContact(String contact) {
		Contact = contact;
	}
	public String getPhone() {
		return Phone;
	}
	public void setPhone(String phone) {
		Phone = phone;
	}
	public String getRemarks() {
		return Remarks;
	}
	public void setRemarks(String remarks) {
		Remarks = remarks;
	}
	
	public String getLoanAgreement() {
		return LoanAgreement;
	}
	public void setLoanAgreement(String loanAgreement) {
		LoanAgreement = loanAgreement;
	}
	

	@Override
	public String toString() {
		return "LoanApplication [ID=" + ID + ", ApplicationDate=" + ApplicationDate + ", Applicant=" + Applicant
				+ ", ApplicationNo=" + ApplicationNo + ", IsReturn=" + IsReturn + ", Area=" + Area + ", LoanDate="
				+ LoanDate + ", ExpectedReturnDate=" + ExpectedReturnDate + ", ActualReturnDate=" + ActualReturnDate
				+ ", CustomerName=" + CustomerName + ", Department=" + Department + ", Contact=" + Contact + ", Phone="
				+ Phone + ", Remarks=" + Remarks + ", GoodsJson=" + GoodsJson + "]";
	}
	
	


}
