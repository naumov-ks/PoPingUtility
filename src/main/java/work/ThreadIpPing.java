package work;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ThreadIpPing implements Callable<TaskOne> {
	private ConcurrentLinkedQueue<TaskOne> ipList;
	

	public ThreadIpPing(ConcurrentLinkedQueue<TaskOne> ipList) {
		this.ipList = ipList;
		
	}

	public TaskOne call() throws Exception {
		if(ipList.poll()!=null){
		TaskOne taskOne = ipList.poll();
		System.out.println("Поток вытащил из коллекции "+ taskOne.getNumber());
		ArrayList<IpAddress> ipList = taskOne.getIpList();
		for (int i = 0; i < ipList.size(); i++) {
			IpAddress ip = ipList.get(i);
			ip = poPing(ip);
		}
		taskOne.setIpList(ipList);
		return taskOne;
		}
		
		System.out.println("Хуйня ебанная");
		return null;
		
	}

	public IpAddress poPing(IpAddress ipPing) throws IOException {
		
		String ip = ipPing.getAddress();
		int answer = 0;
		System.out.println("Начал пинговать "+ip);
		if (ip != null) {
			
			InetAddress host = InetAddress.getByName(ip);
			int i = 0;
			while (i < 5) {
				if (host.isReachable(1500)) {
					answer++;
				}
				i++;
			}
			ipPing.setAnswer(answer);
		}
		System.out.println("Закончил пинговать "+ip);
		
		return ipPing;
	}

}
