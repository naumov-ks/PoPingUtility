package study;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;



public class App {

	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		long startTime = System.currentTimeMillis();
		System.out.println("Зашли в программу");
		
		ListUnitDevices unitList=new ListUnitDevices();
		String pathFile="taskfile.xls";
		unitList.readXlsToList(pathFile);
		int size=unitList.listSize();
		ArrayList<Future<UnitDevice>> future = new ArrayList<Future<UnitDevice>>();
		ExecutorService es = Executors.newFixedThreadPool(4);
		for(int i=0;i<size;i++){
			Future<UnitDevice> z = es.submit(new ThreadPing(unitList));
			future.add(z);
			System.out.println("размер future "+future.size());
		}
		
		ArrayList<UnitDevice> finishList=new ArrayList<UnitDevice>();
		for (int i = 0; i < future.size(); i++) {
		System.out.println(future.size()+" i="+i);
		UnitDevice	result=future.get(i).get();
			finishList.add(result);
			System.out.println("Размер выполнения "+future.size());
		}
		es.shutdown();
		System.out.println("Закончили пинг всех устройств"+future.size());
		finishList.sort(null);
		
		WriterResult writer=new WriterResult();
		writer.writeResultXls("fotoradars.xls", finishList);
		/*
		//Записываем результат в файл
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
		
		File resultFile=new File("result_"+pathFile);
		OutputStream out= new FileOutputStream(resultFile);
		resultXls.write(out);
		resultXls.close();
*/
		
		long estimatedTime = System.currentTimeMillis() - startTime;
		System.out.println(
				"Работа программы завершена в " + estimatedTime / 1000 + "c.");	
	}

}
