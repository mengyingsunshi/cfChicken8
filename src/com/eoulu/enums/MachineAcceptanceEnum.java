package com.eoulu.enums;

public enum MachineAcceptanceEnum {
	
	YES(0,"是"),
	NO(1,"否"),
	NOT_INVOLVE(2,"不涉及"),
	NOT_SELECTED(3,"未选择");
	
	private int code;
	
	private String message;
	
	MachineAcceptanceEnum(int code,String message){
		this.code = code;
		
		this.message = message;
	}

	public int getCode() {
		return code;
	}


	public String getMessage() {
		return message;
	}
	
	public static String getMessage(int code){
		for(MachineAcceptanceEnum each : MachineAcceptanceEnum.class.getEnumConstants()){
			if(each.getCode() == code){
				return each.getMessage();
			}
		}
		return null;
		
	}
	
	public static int getCode(String msg){
		for(MachineAcceptanceEnum each : MachineAcceptanceEnum.class.getEnumConstants()){
			if(each.getMessage().equals(msg)){
				return each.getCode();
			}
		}
		return 3;
		
	}


	
	

}
