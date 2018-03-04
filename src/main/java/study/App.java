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
			//System.out.println("размер future "+future.size());
		}
		
		ArrayList<UnitDevice> finishList=new ArrayList<UnitDevice>();
		for (int i = 0; i < future.size(); i++) {
		//System.out.println(future.size()+" i="+i);
		UnitDevice	result=future.get(i).get();
			finishList.add(result);
			System.out.println("Размер выполнения "+future.size());
		}
		es.shutdown();
		System.out.println("Закончили пинг всех "+future.size()+" узлов.");
		finishList.sort(null);
		
		WriterResult writer=new WriterResult();
		writer.writeResultXls("fotoradars.xls", finishList);
//		
		long estimatedTime = System.currentTimeMillis() - startTime;
		System.out.println(
				"Работа программы завершена в " + estimatedTime / 1000 + "c.");	
	}

}
