package com.eoulu.syn;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.eoulu.util.Java2Word;

public class ExportFAT {
	public String exportFile(List<Map<String, Object>> list){
		HashMap<String, Object> data = new HashMap<>();
		String ContractNO = list.get(1).get("ContractNO").toString();
		String DCNO = list.get(1).get("DCNO").toString();
		String CommoditySpecifications = list.get(1).get("CommoditySpecifications").toString();
		String Qty = list.get(1).get("Qty").toString();
		String Model = list.get(1).get("Model").toString();
		String PriceTerm = list.get(1).get("PriceTerm").toString();
		String Packing = list.get(1).get("Packing").toString();
		String TotalValue = list.get(1).get("TotalValue").toString();
		String Balance = list.get(1).get("Balance").toString();
		String Percent = list.get(1).get("Percent").toString();
		String EndUser = list.get(1).get("EndUser").toString();
		String Beneficiary = list.get(1).get("Beneficiary").toString();
		String Applicant = list.get(1).get("Applicant").toString();
		data.put("${ContractNO}",ContractNO);
		data.put("${DCNO}", DCNO);
		data.put("${CommoditySpecifications}", CommoditySpecifications);
		data.put("${Qty}", Qty);
		data.put("${Model}", Model);
		data.put("${PriceTerm}", PriceTerm);
		data.put("${Packing}", Packing);
		data.put("${TotalValue}", TotalValue);
		data.put("${Balance}", Balance);
		data.put("${Percent}", Percent);
		data.put("${EndUser}", EndUser);
		data.put("${Beneficiary}", Beneficiary);
		data.put("${Applicant}", Applicant);
		
		Java2Word word = new Java2Word();
		String basePath = "E:/LogisticsFile/File/FAT-"+list.get(1).get("ContractNO")+".doc";
		String newName = ExportQuotePDF.copyFile("FAT模板.doc");
		
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
		
	        return "FAT-"+list.get(1).get("ContractNO")+".doc";
        }	
	}
}
