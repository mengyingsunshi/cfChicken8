package com.eoulu.entity;

public class VersionManagement {
	
	private int ID;
	private String password;
	private String from;
	private String ProjectName;
	private String Version;
	private String BoardingTime;
	private String Registrant;
	private String UpdatedContent;
	private String VmClassifyHref;
	private String versionFile;
	private String filePath;
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getProjectName() {
		return ProjectName;
	}
	public void setProjectName(String projectName) {
		ProjectName = projectName;
	}
	public String getVersion() {
		return Version;
	}
	public void setVersion(String version) {
		Version = version;
	}
	public String getBoardingTime() {
		return BoardingTime;
	}
	public void setBoardingTime(String boardingTime) {
		BoardingTime = boardingTime;
	}
	public String getRegistrant() {
		return Registrant;
	}
	public void setRegistrant(String registrant) {
		Registrant = registrant;
	}
	public String getUpdatedContent() {
		return UpdatedContent;
	}
	public void setUpdatedContent(String updatedContent) {
		UpdatedContent = updatedContent;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getVmClassifyHref() {
		return VmClassifyHref;
	}
	public void setVmClassifyHref(String vmClassifyHref) {
		VmClassifyHref = vmClassifyHref;
	}
	public String getVersionFile() {
		return versionFile;
	}
	public void setVersionFile(String versionFile) {
		this.versionFile = versionFile;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	
	

}
