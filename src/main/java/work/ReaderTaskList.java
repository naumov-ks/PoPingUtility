package work;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

public class ReaderTaskList {
//private ConcurrentLinkedQueue<TaskOne> listFilesS=new ConcurrentLinkedQueue<TaskOne>();
private File fileXls;



public ReaderTaskList(String filePath) throws IOException {
	File file=new File(filePath);
	if(!file.exists()){
		file.createNewFile();
	}
	fileXls=file;
}



public ConcurrentLinkedQueue<TaskOne> getIpList() throws IOException{
	ConcurrentLinkedQueue<TaskOne> listFile=new ConcurrentLinkedQueue<TaskOne>();
	InputStream in = new FileInputStream(fileXls);

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
    	
    	listFile.add(new TaskOne(number, name, ipList));
    	
    }
	return listFile;
}


}
