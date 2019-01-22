package com.eoulu.syn;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.eoulu.util.Java2Word;


public class ExportReceiving {
	public String exportFile(List<Map<String, Object>> list){
	
		String TestDate = list.get(1).get("TestDate").toString();
		String ContractNO = list.get(1).get("ContractNO").toString();
		String PONO = list.get(1).get("PONO").toString();
		String Engineer = list.get(1).get("Engineer").toString();
		String Model = list.get(1).get("Model").toString();
		String ConfirmDate = list.get(1).get("ConfirmDate").toString();
		String GuaranteeDate = list.get(1).get("GuaranteeDate").toString();
		String Warranty = list.get(1).get("Warranty").toString();
		String Sender = list.get(1).get("Sender").toString();
		String Receptor = list.get(1).get("Receptor").toString();
		HashMap<String, Object> data = new HashMap<>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar testDate = Calendar.getInstance();
		Calendar confirmDate = Calendar.getInstance();
		Calendar guaranteeDate = Calendar.getInstance();
		try {
			testDate.setTime(simpleDateFormat.parse(TestDate));
			confirmDate.setTime(simpleDateFormat.parse(ConfirmDate));
			guaranteeDate.setTime(simpleDateFormat.parse(GuaranteeDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		data.put("${TestYear}", testDate.get(Calendar.YEAR)+"");
		data.put("${TestMonth}", (testDate.get(Calendar.MONTH)+1)+"");
		data.put("${TestDay}", testDate.get(Calendar.DAY_OF_MONTH)+"");
		if(!PONO.equals("NA")){
			ContractNO+="(PO No.:"+PONO;
		}
		data.put("${ContractNO}",ContractNO);
	
		data.put("${Engineer}",Engineer);
		data.put("${Model}", Model);
		data.put("${ConfirmYear}",confirmDate.get(Calendar.YEAR)+"");
		data.put("${ConfirmMonth}", (confirmDate.get(Calendar.DAY_OF_MONTH)+1)+"");
		data.put("${ConfirmDay}", confirmDate.get(Calendar.DAY_OF_MONTH)+"");
		data.put("${GuaranteeYear}",guaranteeDate.get(Calendar.YEAR)+"");
		data.put("${GuaranteeMonth}", (guaranteeDate.get(Calendar.DAY_OF_MONTH)+1)+"");
		data.put("${GuaranteeDay}", guaranteeDate.get(Calendar.DAY_OF_MONTH)+"");
		data.put("${Warranty}", Warranty);
		data.put("${Sender}", Sender);
		data.put("${Receptor}", Receptor);
		
		
		Java2Word word = new Java2Word();
		String basePath = "E:/LogisticsFile/File/验收报告-"+list.get(1).get("ContractNO")+".doc";
		String newName = ExportQuotePDF.copyFile("验收报告-模板.doc");
        if(newName.equals("error")){
        	return newName;
        }else{
        	String srcPath = "E:/Model/"+newName;
	        word.toWord(srcPath,basePath,data,"end");
	        try {
				FileUtils.forceDelete(new File(srcPath));
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	        return "验收报告-"+list.get(1).get("ContractNO")+".doc";
        }
		
	}

}
