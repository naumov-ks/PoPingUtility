package study;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


public class ListUnitDevices {
private LinkedList<UnitDevice> list=new LinkedList<UnitDevice>();




//Заполняем list из файла
public void readXlsToList(String filePath) throws IOException {
	File file=new File(filePath);
	if(!file.exists()){
		System.out.println("Не найден файл с Ip адресами");
		return;
	}
	InputStream in = new FileInputStream(file);
    HSSFWorkbook workbook = new HSSFWorkbook(in);
    HSSFSheet sheet = workbook.getSheetAt(0);
    int lastrow=sheet.getLastRowNum();
    int lastcolumns = sheet.getRow(0).getPhysicalNumberOfCells();
    
    for(int i=1;i<lastrow;i++){
    	HSSFRow row=sheet.getRow(i);
    	int number=(int)row.getCell(0).getNumericCellValue();
    	String name=row.getCell(1).getStringCellValue();
    	ArrayList<IpAddress> ipList=new ArrayList<IpAddress>();
    	for(int i1=0;i1<7;i1++){
    		String ip=row.getCell(i1+2).getStringCellValue();
    		ipList.add(new IpAddress(ip));
    	    	}
    	list.add(new UnitDevice(number, name, ipList));   	
    }
return;	
}
//получаем юнит из листа
public synchronized UnitDevice getUnitDevice()  {
	return list.pollLast();
}
//size листа
public int listSize() {
	int size=list.size();
	return size;
}

	
}
