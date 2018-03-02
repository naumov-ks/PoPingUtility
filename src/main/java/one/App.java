package one;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import work.IpAddress;
import work.ReaderTaskList;
import work.TaskOne;
import work.ThreadIpPing;

public class App {

	public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException, ExecutionException {
 /*      
		Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet("Фоторадары");
        // Нумерация начинается с нуля
        Row row = sheet.createRow(0); 
        Cell name = row.createCell(0);
        name.setCellValue("192.168.198.100");   
        File file=new File("test.xls");
        book.write(new FileOutputStream(file));
        book.close();
        
        HSSFWorkbook myExcelBook = new HSSFWorkbook(new FileInputStream(file));
        HSSFSheet myExcelSheet = myExcelBook.getSheet("Фоторадары");
        HSSFRow row1 = myExcelSheet.getRow(0);
        
        if(row1.getCell(0).getCellType() == HSSFCell.CELL_TYPE_STRING){
            String name1 = row1.getCell(0).getStringCellValue();
            System.out.println("name : " + name1);
        }
            
        myExcelBook.close();
*/      
		
		
		ReaderTaskList ex=new ReaderTaskList("taskfile.xls");
		ConcurrentLinkedQueue<TaskOne> ipList=ex.getIpList();
		int size=ipList.size();
		
		
		ArrayList<Future<TaskOne>> future = new ArrayList<Future<TaskOne>>();
		ExecutorService es = Executors.newFixedThreadPool(4);
		
		for(int i=0;i<size;i++){
			Future<TaskOne> z = es.submit(new ThreadIpPing(ipList));
			future.add(z);
			System.out.println("размер future "+future.size());
		}

		
			/*
		for(int i = 0; i < ipList.size(); i++){
			Future<TaskOne> z = es.submit(new ThreadIpPing(ipList, i));
			future.add(z);
		}
		*/
		
		ArrayList<TaskOne> finishList=new ArrayList<TaskOne>();
		for (int i = 0; i < future.size(); i++) {
		System.out.println(future.size()+" i="+i);
			TaskOne	result=future.get(i).get();
			finishList.add(result);
			
		}
        for(TaskOne x:finishList){
        	System.out.println(x.getName()+" номер "+x.getNumber()+" ");
        	for(IpAddress y:x.getIpList()){
        		System.out.print(y.getAddress()+" ");
        	}
        	System.out.println("");
        }
        System.out.println("The end");
		
	}

}
