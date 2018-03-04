package study;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.Callable;

public class ThreadPing implements Callable<UnitDevice> {
	private ListUnitDevices list;

	public ThreadPing(ListUnitDevices list) {
		this.list = list;
	}

	public UnitDevice call() throws Exception {
		UnitDevice unit = list.getUnitDevice();
		if (unit == null) {
			System.out.println("Закончились юниты в list");
			return null;
		}
		System.out.println(Thread.currentThread().getName() + " вытащил из листа " + unit.getName());
		ArrayList<IpAddress> ipList = unit.getIpList();
		for (int i = 0; i < ipList.size(); i++) {
			IpAddress ip = ipList.get(i);
			poPing(ip);
		}
		unit.setIpList(ipList);
		System.out.println(Thread.currentThread().getName() + " закончил пинговать " + unit.getName());
		return unit;
	}

	// Пингуем и записываем результат
	public void poPing(IpAddress ipPing) throws IOException {
		String ip = ipPing.getAddress();
		int answer = 0;
		System.out.println(Thread.currentThread().getName() +" начал пинговать " + ip);
		if (ip != "") {
			InetAddress host = InetAddress.getByName(ip);
			boolean ping = host.isReachable(1000);
			if (!ping) {
				int i = 0;
				while (i < 1) {
					if (host.isReachable(1000)) {
						answer++;
					}
					i++;
				}
			} else
				answer++;

			if (answer > 0) {
				ipPing.setAnswer(1);
			} else
				ipPing.setAnswer(0);
		} else {
			System.out.println("Пингуешь пустую строку!");
		return;
		}
		System.out.println(Thread.currentThread().getName() + " закончил пинговать " + ip+"с результатом answer "+ answer);
		return;
	}

}
