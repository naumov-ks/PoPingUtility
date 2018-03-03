package study;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class WriterResult {

public void writeResultXls(String pathFile, ArrayList<UnitDevice> finishList) throws IOException {	
	
	long curTime = System.currentTimeMillis(); 
	String date=new SimpleDateFormat("dd.MM.yyyy").format(curTime);
	
	HSSFWorkbook resultXls = new HSSFWorkbook();
    HSSFSheet sheet = resultXls.createSheet("Фоторадары");
   // Нумерация начинается с нуля
    for(int i=0;i<finishList.size();i++) {
   UnitDevice unit=finishList.get(i);
   HSSFRow row = sheet.createRow(i);
   HSSFCell number=row.createCell(0);
   number.setCellValue(unit.getNumber());
   HSSFCell name=row.createCell(1);
   name.setCellValue(unit.getName());
   
   ArrayList<IpAddress> ipList=unit.getIpList();
   for(int i1=0;i1<7;i1++){
   	HSSFCell ip=row.createCell(i1+2);
   	ip.setCellValue(ipList.get(i1).getAddress());
   	HSSFCell answer=row.createCell(i1+9);
   	answer.setCellValue(ipList.get(i1).getAnswer());
	    	}
   		
    }
    
    for(int i=1;i<16;i++) {
   	 sheet.autoSizeColumn(i);
    }
	
	File resultFile=new File(date+"_"+pathFile);
	OutputStream out= new FileOutputStream(resultFile);
	resultXls.write(out);
	resultXls.close();	
}
	


}
