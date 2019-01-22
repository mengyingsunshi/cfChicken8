package com.eoulu.service.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.junit.Test;

import com.eoulu.commonality.Page;
import com.eoulu.dao.PromotionDataDao;
import com.eoulu.service.PromotionDataService;
import com.eoulu.util.DocumentUploadUtilY;
import com.eoulu.util.EXCELUtil;
import com.eoulu.util.GeneratePdfUtil;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class PromotionServiceImpl implements PromotionDataService {
	private static BaseColor titleColor = null;
	private static Font textFont = null;
	private static BaseColor highColor = null;
	static{
		BaseFont bfChinese = null;
		
		try {
			bfChinese = BaseFont.createFont("C:/Windows/Fonts/msyh.ttc,1", BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);
		} catch (DocumentException e2) {
			e2.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		

		textFont = new Font(bfChinese, 12, Font.NORMAL); // 正常
		titleColor = new BaseColor(212,212,212);
		highColor = new BaseColor(255,255,0);
		
	}

	

	@Override
	public String getPath(String date,String sheet) {
		PromotionDataDao dao = new PromotionDataDao();
		List<Map<String, Object>> list = dao.getFileName(date);
		String excelName = "";
		if(list.size()>1){
			excelName = list.get(1).get("FileName").toString();
			
		}
		String path = "LogisticsFile/File/PromotionData/"+excelName + "-" +sheet+".pdf";
		
		return path;
	}

	@Override
	public boolean uploadExcel(HttpServletRequest request) {
		PromotionDataDao dao = new PromotionDataDao();
		DocumentUploadUtilY utilY = new DocumentUploadUtilY();
		Map<String,String > map = utilY.uploadNotRename(request, "E:/LogisticsFile/File/PromotionData");
		String path = map.get("Path");
		String excelName = map.get("FileName"); 
		excelName = excelName.substring(0, excelName.indexOf("."));
		String date = excelName.substring(excelName.lastIndexOf("_")+1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		Date date2 = null;
		try {
			date2 = sdf.parse(date);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		date = sdf.format(date2);
		System.out.println("-------------------"+date);
		InputStream in = null;
		try {
			in = new FileInputStream(path);

			excelToPdf(excelName,in);
		
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		dao.delete(date);
		boolean flag = dao.add(excelName, date);
		return flag;

	}
	


	@Override
	public boolean excelToPdf(String excelName, InputStream in) throws IOException {


		EXCELUtil excelUtil = new EXCELUtil();
		HSSFWorkbook xwb = null;

		xwb = new HSSFWorkbook(in);
		
		String pdfName = "E:/LogisticsFile/File/PromotionData/"+excelName+"-";
		String sheetName = "Eoulu公司网站访问量统计表 ";
		HSSFSheet sheet1 = xwb.getSheet(sheetName);
		PdfPTable table1 = setPdfTable(sheet1, 4,new float[]{150f,100f,100f,100f}, 1, excelUtil);
		GeneratePdfUtil.createWebDataPdf(pdfName+sheetName.trim()+".pdf",table1,null, sheetName);
		
		sheetName = "百度推广IP明细";
		HSSFSheet sheet2 = xwb.getSheet(sheetName);
		PdfPTable table2 = setPdfTable(sheet2, 2,new float[]{150f,300f}, 0, excelUtil);
		GeneratePdfUtil.createWebDataPdf(pdfName+sheetName.trim()+".pdf",table2,null, sheetName);
		
		
		sheetName = "360后台IP明细";
		HSSFSheet sheet3 = xwb.getSheet(sheetName);
		PdfPTable table3 = setPdfTable(sheet3, 2,new float[]{150f,300f}, 0, excelUtil);
		GeneratePdfUtil.createWebDataPdf(pdfName+sheetName.trim()+".pdf",table3,null, sheetName);
		
		sheetName = "关键词及消费明细";
		HSSFSheet sheet4 = xwb.getSheet(sheetName);
		List<PdfPTable> table4 = setPdfTableBy2(sheet4, 3, new float[]{150f,150f,150f}, excelUtil);
		GeneratePdfUtil.createWebDataPdf(pdfName+sheetName.trim()+".pdf",table4.get(0),table4.get(1), sheetName);
		
		try {
			xwb.close();
		} catch (IOException e){
			e.printStackTrace();
		}
		return true;
	}
	
	public PdfPCell setPdfCell(String cellTxt,boolean highlight,boolean title){
		
		PdfPCell cell = new PdfPCell(new Paragraph(cellTxt, textFont));
		
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		if(highlight){
			cell.setBackgroundColor(highColor);
		}
		if(title){
			cell.setBackgroundColor(titleColor);
		}
		
		cell.setFixedHeight(30);
		return cell;
			
	}
	
	public PdfPCell setRegionPdfCell(String cellTxt,boolean title){
		
		PdfPCell cell = new PdfPCell(new Paragraph(cellTxt, textFont));
		
		cell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	
		if(title){
			cell.setBackgroundColor(titleColor);
		}
		
		cell.setFixedHeight(30);
		cell.setColspan(3);
		return cell;
			
	}
	
	public PdfPTable setPdfTable(HSSFSheet sheet,int colNum,float[] width,int order,EXCELUtil excelUtil){
		
		HSSFRow xrCol = sheet.getRow(0);
		PdfPTable table = new PdfPTable(colNum);
	
		try {
			table.setTotalWidth(width);
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
		
		table.setLockedWidth(true);
		for(int m=0; m<colNum; m++){
			HSSFCell xcCol = xrCol.getCell(m);
			String colName = xcCol.getStringCellValue();
			PdfPCell cell = setPdfCell(colName,false,true);
			table.addCell(cell);
	
		}

		int rowsCount = sheet.getLastRowNum();
		System.out.println("rows----"+rowsCount);
		if(order == 1){
			for(int j=rowsCount; j>0; j--){
				HSSFRow xr = sheet.getRow(j);
		
				for(int k=0; k<colNum; k++){
					HSSFCell xc = null;
					try{
						xc = xr.getCell(k);
					}catch(NullPointerException e){
						break;
					}
					String value = excelUtil.getCellValue(xc);
					PdfPCell cell = setPdfCell(value,false,false);
					table.addCell(cell);
					
				}
				
			}
		}else{
			for(int j=1; j<=rowsCount; j++){
				HSSFRow xr = sheet.getRow(j);
				for(int k=0; k<colNum; k++){
					HSSFCell xc = null;
					try{
						xc = xr.getCell(k);
					}catch(NullPointerException e){
						break;
					}
					String value = excelUtil.getCellValue(xc);
					short color = xc.getCellStyle().getFillForegroundColor();
					PdfPCell cell = null;
					if(color!=64){
						cell = setPdfCell(value,true,false);
					}else {
						cell = setPdfCell(value, false, false);
					}
					
					table.addCell(cell);
					
	
				}
				
			}
			
		}
		return table;
			
	}
	
	public List<PdfPTable> setPdfTableBy2(HSSFSheet sheet,int colNum,float[] width,EXCELUtil excelUtil){
		List<PdfPTable> list = new ArrayList<>();
		int rowsCount = sheet.getLastRowNum();
		String firstTitle = "",secondTitle = "";
		int secondNum = 0;
		for(int i = 0;i <= rowsCount;i ++){
			String txt = excelUtil.getMergedRegionValue(sheet, i, 0);
			if(txt != null ){
				if(i == 0){
					firstTitle = txt;
				}else{
					secondTitle = txt;
					secondNum = i;
				}
			}
		}
		PdfPCell titleCell1 = setRegionPdfCell(firstTitle, false);
		PdfPCell titleCell2 = setRegionPdfCell(secondTitle, false);
		
	
		HSSFRow rowl = sheet.getRow(1);
		PdfPTable table = new PdfPTable(colNum);
		try {
			table.setTotalWidth(width);
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
		table.setLockedWidth(true);
		table.addCell(titleCell1);
		for(int m=0; m<colNum; m++){
			HSSFCell xcCol = rowl.getCell(m);
			String colName = xcCol.getStringCellValue();
			PdfPCell cell = setPdfCell(colName,false,true);
			table.addCell(cell);
	
		}

		for(int j=2; j<secondNum-1; j++){
			HSSFRow xr = sheet.getRow(j);
			for(int k=0; k<colNum; k++){
				HSSFCell xc = xr.getCell(k);

				String value = excelUtil.getCellValue(xc);
				short color = xc.getCellStyle().getFillForegroundColor();
				PdfPCell cell = null;
				if(color!=64){
					cell = setPdfCell(value,true,false);
				}else {
					cell = setPdfCell(value, false, false);
				}
				
				table.addCell(cell);
				
			}
			
		}
		list.add(table);
		
		PdfPTable table2 = new PdfPTable(colNum);
		try {
			table2.setTotalWidth(width);
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
		table2.setLockedWidth(true);
		table2.addCell(titleCell2);
		HSSFRow row2 = sheet.getRow(secondNum+1);
		for(int m=0; m<colNum; m++){
			HSSFCell xcCol = row2.getCell(m);
			String colName = xcCol.getStringCellValue();
			PdfPCell cell = setPdfCell(colName,false,true);
			table2.addCell(cell);
	
		}

		for(int j=secondNum+2; j<=rowsCount; j++){
			HSSFRow xr = sheet.getRow(j);
		
			for(int k=0; k<colNum; k++){
				HSSFCell xc = null;
				try{
					xc = xr.getCell(k);
				}catch(NullPointerException e){
					break;
				}

				String value = excelUtil.getCellValue(xc);
				short color = xc.getCellStyle().getFillForegroundColor();
				PdfPCell cell = null;
				if(color!=64){
					cell = setPdfCell(value,true,false);
				}else {
					cell = setPdfCell(value, false, false);
				}
				
				table2.addCell(cell);

			}
			
		}
		list.add(table2);
			
		
		return list;
			
	}
	
	@Test
	public void test(){
		PromotionDataDao dao = new PromotionDataDao();
		String path = "C:/Users/zhuoz/Downloads/Eoulu公司网站数据统计表_190102.xls";

		String excelName = "Eoulu公司网站数据统计表_190102.xls";
		excelName = excelName.substring(0, excelName.indexOf("."));
		String date = excelName.substring(excelName.lastIndexOf("_")+1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
		Date date2 = null;
		try {
			date2 = sdf.parse(date);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		sdf = new SimpleDateFormat("yyyy-MM-dd");
		date = sdf.format(date2);
		InputStream in = null;
		try {
			in = new FileInputStream(path);

			//excelToPdf(0, "Eoulu公司网站访问量统计表 ",excelName,in, 0,new float[]{150f,100f,100f,100f});
			excelToPdf(excelName, in);
			//excelToPdf(0, "360后台IP明细", excelName, in, 3,new float[]{150f,300f} );
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		boolean flag = dao.add(excelName, date);
		System.out.println("-----"+flag);
		

	}

	@Override
	public List<Map<String, Object>> getDate(Page page) {
		PromotionDataDao dao = new PromotionDataDao();
		return dao.getDateList(page);
	}

	@Override
	public int getCounts() {
		PromotionDataDao dao = new PromotionDataDao();
		return dao.getCounts();
	}


}
