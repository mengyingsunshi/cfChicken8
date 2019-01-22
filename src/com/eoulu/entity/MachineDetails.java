package com.eoulu.entity;

import java.util.List;
import java.util.Map;

public class MachineDetails {

	private int ID;
	private String unit;
	private String Model;
	private String SN;
	private String ContractNO;
	private String InstalledTime;
	private String OperatingTime;
	private int CustomerID;
	private int Status;
	private String Responsible;
	private String CurrentProgress;
	private int IsNormal;
	
	private List<Map<String, Object>> dateList;
	private List<Map<String, String>> progressList;
	public int getCustomerID() {
		return CustomerID;
	}
	public void setCustomerID(int customerID) {
		CustomerID = customerID;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	
	
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getModel() {
		return Model;
	}
	public void setModel(String model) {
		Model = model;
	}
	public String getSN() {
		return SN;
	}
	public void setSN(String sN) {
		SN = sN;
	}
	public String getContractNO() {
		return ContractNO;
	}
	public void setContractNO(String contractNO) {
		ContractNO = contractNO;
	}
	public String getInstalledTime() {
		return InstalledTime;
	}
	public void setInstalledTime(String installedTime) {
		InstalledTime = installedTime;
	}
	public String getOperatingTime() {
		return OperatingTime;
	}
	public void setOperatingTime(String operatingTime) {
		OperatingTime = operatingTime;
	}
	public int getStatus() {
		return Status;
	}
	public void setStatus(int status) {
		Status = status;
	}
	public String getResponsible() {
		return Responsible;
	}
	public void setResponsible(String responsible) {
		Responsible = responsible;
	}
	public String getCurrentProgress() {
		return CurrentProgress;
	}
	public void setCurrentProgress(String currentProgress) {
		CurrentProgress = currentProgress;
	}
	public List<Map<String, Object>> getDateList() {
		return dateList;
	}
	public void setDateList(List<Map<String, Object>> dateList) {
		this.dateList = dateList;
	}
	public List<Map<String,String>> getProgressList() {
		return progressList;
	}
	public void setProgressList(List<Map<String, String>> progressList) {
		this.progressList = progressList;
	}
	public int getIsNormal() {
		return IsNormal;
	}
	public void setIsNormal(int isNormal) {
		IsNormal = isNormal;
	}
	

	
	
}
